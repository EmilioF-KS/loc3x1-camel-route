#!/usr/bin/env python3
"""
BPEL Analysis for LOC-002
Parses LocationRetrievalLOC3X1Process.bpel to extract:
- Inbound operations
- Partner links
- Invoke sequence per inbound op
- Fault handlers and SimpleFault replies
Generates doc/bpel-analysis.md with Mermaid sequence diagrams.
Also generates orchestration/spec.yaml describing inbound flows and mediation order.
"""
import re
import os
import sys
from pathlib import Path
from datetime import datetime
import xml.etree.ElementTree as ET
import argparse
import warnings

# Suppress noisy ResourceWarning from lower-level HTTP clients during CLI runs
warnings.simplefilter("ignore", ResourceWarning)

# Ensure project root is on sys.path for local imports
SCRIPT_DIR = Path(__file__).resolve().parent
PROJECT_ROOT = SCRIPT_DIR.parent
if str(PROJECT_ROOT) not in sys.path:
    sys.path.insert(0, str(PROJECT_ROOT))

from agents.bpel_agent import BpelAgent

BPWS = "http://schemas.xmlsoap.org/ws/2004/03/business-process/"
NS = {"bpws": BPWS}

# Helper to derive external partner names (excluding inbound interface)
def get_external_partners(partner_links):
    names = {pl.get("name") for pl in partner_links if pl.get("name")}
    names.discard("LocationRetrievalLOC3X1B")
    return names

# Argument parsing for dynamic root/bpel/output paths
def get_args():
    default_output = str((SCRIPT_DIR.parent / "doc" / "bpel-analysis.md").resolve())
    parser = argparse.ArgumentParser(description="Analyze BPEL orchestration and generate Markdown report")
    parser.add_argument("--root", type=str, default=os.environ.get("CONTRACTS_ROOT", str(PROJECT_ROOT)), help="Root directory to search for BPEL and related files")
    parser.add_argument("--bpel", type=str, default="", help="Explicit BPEL file path to analyze")
    parser.add_argument("--output", type=str, default=default_output, help="Output Markdown path")
    parser.add_argument("--spec", type=str, default=str((SCRIPT_DIR.parent / "orchestration" / "spec.yaml").resolve()), help="Output orchestration spec YAML path")
    parser.add_argument("--use-agent", action="store_true", help="Use agent (Ollama+LangGraph) if available for analysis")
    return parser.parse_args()

# Dynamic discovery for BPEL under a root path or explicit file
def find_existing_bpel_path(root: Path, explicit: str | None = None):
    if explicit:
        p = Path(explicit)
        if p.exists():
            return p
        else:
            raise FileNotFoundError(f"Explicit BPEL path not found: {explicit}")
    candidates = list(Path(root).rglob("*.bpel"))
    if candidates:
        for c in candidates:
            if c.name == "LocationRetrievalLOC3X1Process.bpel":
                return c
        return candidates[0]
    raise FileNotFoundError(f"No .bpel file found under root: {root}")

# XML helpers

def parse_partner_links(root):
    pls = []
    for pl in root.findall(f".//{{{BPWS}}}partnerLink"):
        pls.append({
            "name": pl.get("name", pl.get("myRole", "")),
            "myRole": pl.get("myRole", ""),
            "partnerRole": pl.get("partnerRole", ""),
            "type": pl.get("partnerLinkType", ""),
        })
    return pls


def parse_on_messages(root):
    on_msgs = []
    for on in root.findall(f".//{{{BPWS}}}onMessage"):
        on_msgs.append({
            "operation": on.get("operation", ""),
            "partnerLink": on.get("partnerLink", ""),
            "portType": on.get("portType", ""),
        })
    return on_msgs


def extract_sequences_text(text):
    on_msg_pattern = re.compile(r"<bpws:onMessage[^>]*operation=\"([^\"]+)\"[\s\S]*?>", re.IGNORECASE)
    invoke_pattern = re.compile(r"<bpws:invoke[^>]*name=\"([^\"]+)\"[^>]*operation=\"([^\"]*)\"[^>]*partnerLink=\"([^\"]*)\"", re.IGNORECASE)
    catch_pattern = re.compile(r"<bpws:catch[^>]*faultName=\"([^\"]+)\"[^>]*faultMessageType=\"([^\"]+)\"[^>]*faultVariable=\"([^\"]+)\"", re.IGNORECASE)
    reply_fault_pattern = re.compile(r"<bpws:reply[^>]*faultName=\"([^\"]+)\"[^>]*operation=\"([^\"]+)\"", re.IGNORECASE)
    on_msgs = []
    for m in on_msg_pattern.finditer(text):
        on_msgs.append({"operation": m.group(1), "start": m.start()})
    blocks = []
    for i in range(len(on_msgs)):
        start = on_msgs[i]["start"]
        end = on_msgs[i + 1]["start"] if i + 1 < len(on_msgs) else len(text)
        block_text = text[start:end]
        invokes = [
            {"name": im.group(1), "operation": im.group(2), "partnerLink": im.group(3)}
            for im in invoke_pattern.finditer(block_text)
            if im.group(2) and im.group(3) and im.group(2).lower() != "null" and im.group(3).lower() != "null"
        ]
        catches = [
            {"faultName": cm.group(1), "faultMessageType": cm.group(2), "faultVariable": cm.group(3)}
            for cm in catch_pattern.finditer(block_text)
        ]
        reply_faults = [
            {"faultName": rf.group(1), "operation": rf.group(2)}
            for rf in reply_fault_pattern.finditer(block_text)
        ]
        blocks.append({
            "operation": on_msgs[i]["operation"],
            "start": start,
            "end": end,
            "invokes": invokes,
            "catches": catches,
            "replyFaults": reply_faults,
        })
    return blocks


def generate_mermaid(on_message, allowed_partners):
    seq = [
        inv for inv in on_message.get("invokes", [])
        if inv.get("partnerLink") in allowed_partners and inv.get("operation") and inv["operation"].lower() != "null"
    ]
    lines = ["```mermaid", "sequenceDiagram", "participant Inbound as LOC3X1B", "participant M as LOC3X1M", "participant SP as CRP11X1", "participant C as CRP10X1"]
    for inv in seq:
        pl = inv["partnerLink"]
        op = inv["operation"]
        if pl and op:
            lines.append(f"Inbound->>{pl}: {op}")
    lines.append("```")
    return "\n".join(lines)


def write_markdown(md_path, process_name, partner_links, on_messages, source_file):
    md_path = Path(md_path)
    md_path.parent.mkdir(parents=True, exist_ok=True)
    allowed = get_external_partners(partner_links)
    with open(md_path, "w", encoding="utf-8") as f:
        f.write("# BPEL Analysis\n\n")
        f.write(f"**Generated:** {datetime.now().isoformat()}\n")
        f.write(f"**Source:** `{source_file}`\n\n")
        f.write("## Process\n\n")
        f.write(f"- **Name:** `{process_name}`\n")
        f.write("- **Target Namespace:** `http://LocationServices`\n\n")
        f.write("## Partner Links\n\n")
        for pl in partner_links:
            f.write(f"- `{pl['name']}` (type: {pl['type']}, myRole: {pl['myRole']}, partnerRole: {pl['partnerRole']})\n")
        f.write("\n## Inbound Operations\n\n")
        for om in on_messages:
            f.write(f"### {om['operation']}\n\n")
            f.write(f"- **PartnerLink:** `{om.get('partnerLink', '')}`\n")
            f.write(f"- **PortType:** `{om.get('portType', '')}`\n")
            seq = [
                inv for inv in om.get("invokes", [])
                if inv.get("partnerLink") in allowed and inv.get("operation") and inv["operation"].lower() != "null"
            ]
            if seq:
                f.write("- **Invocation Order:**\n")
                for inv in seq:
                    f.write(f"  - {inv['partnerLink']} -> `{inv['operation']}`\n")
            catches = om.get("catches", [])
            if catches:
                f.write("- **Fault Handlers:**\n")
                for c in catches:
                    f.write(f"  - Catch `{c['faultName']}` ({c['faultMessageType']}) var `{c['faultVariable']}`\n")
            replies = om.get("replyFaults", [])
            if replies:
                f.write("- **Fault Replies:**\n")
                for rf in replies:
                    f.write(f"  - Reply fault `{rf['faultName']}` on op `{rf['operation']}`\n")
            f.write("\n#### Sequence Diagram\n\n")
            f.write(generate_mermaid(om, allowed))
            f.write("\n\n")


def derive_mediation_order(on_message, allowed_partners):
    seen = []
    for inv in on_message.get("invokes", []):
        pl = inv.get("partnerLink")
        if pl in allowed_partners and pl not in seen:
            seen.append(pl)
    return seen



def write_spec_yaml(spec_path, process_name, source_file, partner_links, on_messages, data_mappings=None):
    spec_path = Path(spec_path)
    spec_path.parent.mkdir(parents=True, exist_ok=True)
    allowed = get_external_partners(partner_links)
    lines = []
    lines.append("process:")
    lines.append(f"  name: {process_name}")
    lines.append(f"  source: {source_file}")
    lines.append("inbound:")
    for om in on_messages:
        lines.append("  - operation: " + (om.get("operation", "") or ""))
        lines.append("    partnerLink: " + (om.get("partnerLink", "") or ""))
        lines.append("    portType: " + (om.get("portType", "") or ""))
        order = derive_mediation_order(om, allowed)
        # Write mediation order as a simple YAML list
        if order:
            joined = ", ".join(order)
            lines.append(f"    mediation_order: [{joined}]")
        # Invokes
        lines.append("    invokes:")
        for inv in om.get("invokes", []):
            pl = inv.get("partnerLink", "")
            op = inv.get("operation", "")
            name = inv.get("name", "")
            lines.append(f"      - partnerLink: {pl}")
            lines.append(f"        operation: {op}")
            if name:
                lines.append(f"        name: {name}")
        # Assignments (bpws:assign) - may be empty in IBM BPEL with scripts
        lines.append("    assignments:")
        for m in om.get("assigns", []) or []:
            lines.append("      -")
            for k in ["fromVar","fromPart","fromXpath","toVar","toPart","toXpath","expression","literal","note"]:
                v = m.get(k, "")
                if v:
                    lines.append(f"        {k}: {v}")
        # Faults
        lines.append("    faults:")
        lines.append("      catches:")
        for c in om.get("catches", []):
            lines.append(f"        - faultName: {c.get('faultName','')}")
            lines.append(f"          faultMessageType: {c.get('faultMessageType','')}")
            lines.append(f"          faultVariable: {c.get('faultVariable','')}")
        lines.append("      replies:")
        for rf in om.get("replyFaults", []):
            lines.append(f"        - faultName: {rf.get('faultName','')}")
            lines.append(f"          operation: {rf.get('operation','')}")
    # Deterministic data mappings extracted from original .map XML artefacts
    lines.append("data_mappings:")
    if data_mappings:
        for dm in data_mappings:
            lines.append("  - mapper: " + dm.get("mapper", ""))
            if dm.get("file"):
                lines.append("    source_file: " + dm["file"])  # relative to root
            lines.append("    source_type: " + dm.get("source_type", ""))
            lines.append("    target_type: " + dm.get("target_type", ""))
            lines.append("    mappings:")
            for e in dm.get("mappings", []):
                lines.append("      - index: " + str(e.get("index", "")))
                if e.get("note"):
                    lines.append("        note: " + e["note"])
                lines.append("        from: " + (e.get("from", "") or ""))
                lines.append("        to: " + (e.get("to", "") or ""))
    content = "\n".join(lines) + "\n"
    with open(spec_path, "w", encoding="utf-8") as f:
        f.write(content)

# Agent-aware and default (regex/XML) analysis paths

def main():
    args = get_args()
    root_dir = Path(args.root).resolve()
    out_md = Path(args.output).resolve()
    out_spec = Path(args.spec).resolve()
    bpel_path = find_existing_bpel_path(root_dir, args.bpel or None)

    # Always attempt deterministic mapper extraction from original .map XML artefacts
    data_mappings = []
    try:
        data_mappings.extend(extract_map_xml_mappings(root_dir))
    except Exception as e:
        print(f"[analyze_bpel] map XML extraction failed: {e}")

    if args.use_agent:
        try:
            agent = BpelAgent()
            analysis = agent.analyze(bpel_path)
            partner_links = analysis.partner_links
            on_messages = []
            for op in analysis.inbound_ops:
                on_messages.append({
                    "operation": op.operation,
                    "partnerLink": op.partnerLink,
                    "portType": op.portType,
                    "invokes": [{"name": i.name, "operation": i.operation, "partnerLink": i.partnerLink} for i in op.invokes],
                    "catches": op.catches,
                    "replyFaults": op.replyFaults,
                    "assigns": getattr(op, 'assigns', []),
                })
            source_rel = str(bpel_path.relative_to(root_dir)) if hasattr(bpel_path, 'is_relative_to') and bpel_path.is_relative_to(root_dir) else str(bpel_path)
            write_markdown(out_md, analysis.process_name, partner_links, on_messages, source_rel)
            write_spec_yaml(out_spec, analysis.process_name, source_rel, partner_links, on_messages, data_mappings)
            print(f"✓ Generated {out_md}")
            print(f"✓ Generated {out_spec}")
            return
        except Exception as e:
            print(f"[analyze_bpel] Agent analysis failed ({e}); falling back to deterministic XML parsing.")

    text = bpel_path.read_text(encoding="utf-8", errors="ignore")
    root = ET.fromstring(text)
    process_name = root.get("name", bpel_path.stem)
    partner_links = parse_partner_links(root)
    on_msgs_meta = parse_on_messages(root)
    on_msgs_blocks = extract_sequences_text(text)
    op_to_meta = {m["operation"]: m for m in on_msgs_meta}
    merged = []
    for block in on_msgs_blocks:
        meta = op_to_meta.get(block["operation"], {})
        block["partnerLink"] = meta.get("partnerLink", "")
        block["portType"] = meta.get("portType", "")
        merged.append(block)
    source_rel = str(bpel_path.relative_to(root_dir)) if hasattr(bpel_path, 'is_relative_to') and bpel_path.is_relative_to(root_dir) else str(bpel_path)
    write_markdown(out_md, process_name, partner_links, merged, source_rel)
    write_spec_yaml(out_spec, process_name, source_rel, partner_links, merged, data_mappings)
    print(f"✓ Generated {out_md}")
    print(f"✓ Generated {out_spec}")


# Deterministic extraction from IBM .map XML artefacts

def extract_map_xml_mappings(root_dir: Path):
    def local(tag: str) -> str:
        return tag.split("}")[-1] if "}" in tag else tag

    mappings = []
    candidates = list(root_dir.rglob("*.map")) + list(root_dir.rglob("*.xml"))
    for cf in candidates:
        try:
            text = cf.read_text(encoding="utf-8", errors="ignore")
        except Exception:
            continue
        if "businessObjectMap" not in text:
            continue
        try:
            root = ET.fromstring(text)
        except Exception:
            continue
        if local(root.tag) != "businessObjectMap":
            # Find the first child that is a businessObjectMap
            bom = None
            for child in root.iter():
                if local(child.tag) == "businessObjectMap":
                    bom = child
                    break
        else:
            bom = root
        if bom is None:
            continue
        mapper_name = ""
        source_type = ""
        target_type = ""
        entries = []
        for child in bom:
            lname = local(child.tag)
            if lname == "name":
                mapper_name = (child.text or "").strip()
            elif lname == "inputBusinessObjectVariable":
                source_type = child.get("name", "")
            elif lname == "outputBusinessObjectVariable":
                target_type = child.get("name", "")
            elif lname == "propertyMap":
                try:
                    idx = int(child.get("executionOrder", "0"))
                except Exception:
                    idx = 0
                move = None
                for sub in child:
                    if local(sub.tag) == "move":
                        move = sub
                        break
                if move is None:
                    continue
                in_prop = ""
                out_prop = ""
                note = ""
                for mchild in move:
                    ml = local(mchild.tag)
                    if ml == "input":
                        in_prop = mchild.get("property", "")
                    elif ml == "output":
                        out_prop = mchild.get("property", "")
                if in_prop or out_prop:
                    entries.append({"index": idx, "note": note, "from": in_prop, "to": out_prop})
        if entries:
            mappings.append({
                "mapper": mapper_name or cf.stem,
                "source_type": source_type,
                "target_type": target_type,
                "file": str(cf.relative_to(root_dir)) if hasattr(cf, 'relative_to') else str(cf),
                "mappings": sorted(entries, key=lambda e: e["index"]),
            })
    return mappings

if __name__ == "__main__":
    main()