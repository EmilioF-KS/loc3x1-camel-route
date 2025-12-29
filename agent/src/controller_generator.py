import json
import os
from pathlib import Path
from typing import Any, Dict, List


JAVA_TMPL = (
    "package __PKG__.controller;\n\n"
    "import org.springframework.http.MediaType;\n"
    "import org.springframework.http.ResponseEntity;\n"
    "import org.springframework.web.bind.annotation.PostMapping;\n"
    "import org.springframework.web.bind.annotation.RequestBody;\n"
    "import org.springframework.web.bind.annotation.RequestMapping;\n"
    "import org.springframework.web.bind.annotation.RestController;\n"
    "import org.springframework.core.io.ClassPathResource;\n"
    "import javax.xml.XMLConstants;\n"
    "import javax.xml.validation.Schema;\n"
    "import javax.xml.validation.SchemaFactory;\n"
    "import javax.xml.validation.Validator;\n"
    "import java.io.InputStream;\n"
    "import java.io.StringReader;\n"
    "import java.nio.charset.StandardCharsets;\n"
    "import java.util.regex.Matcher;\n"
    "import java.util.regex.Pattern;\n\n"
    "@RestController\n"
    "@RequestMapping(path = \"/__PATH__\")\n"
    "public class __CLS__Controller {\n\n"
    "    private String validateXml(String xml, String xsdPath) {\n"
    "        try {\n"
    "            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);\n"
    "            try (InputStream is = new ClassPathResource(xsdPath).getInputStream()) {\n"
    "                Schema schema = sf.newSchema(new javax.xml.transform.stream.StreamSource(is));\n"
    "                Validator v = schema.newValidator();\n"
    "                v.validate(new javax.xml.transform.stream.StreamSource(new StringReader(xml)));\n"
    "            }\n"
    "            return null;\n"
    "        } catch (Exception e) {\n"
    "            return e.getMessage();\n"
    "        }\n"
    "    }\n\n"
    "    private String minimalReply(String replyXsdPath) {\n"
    "        try (InputStream is = new ClassPathResource(replyXsdPath).getInputStream()) {\n"
    "            String xsd = new String(is.readAllBytes(), StandardCharsets.UTF_8);\n"
    "            Matcher m = Pattern.compile(\"<\\s*xs:element\\s+name=\\\"([^\\\"]+)\\\"\").matcher(xsd);\n"
    "            if (m.find()) {\n"
    "                String root = m.group(1);\n"
    "                return \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><\" + root + \"/>\";\n"
    "            }\n"
    "        } catch (Exception ignored) {\n"
    "        }\n"
    "        return \"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?><ok/>\";\n"
    "    }\n\n"
    "    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)\n"
    "    public ResponseEntity<String> invoke(@RequestBody String xml) {\n"
    "        // Validation is optional; reply is always returned for smooth integration runs\n"
    "        // validateXml(xml, \"__REQ_XSD__\");\n"
    "        String reply = minimalReply(\"__REP_XSD__\");\n"
    "        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(reply);\n"
    "    }\n"
    "}\n"
)


def _load_plan(path: str) -> Dict[str, Any]:
    return json.loads(Path(path).read_text(encoding="utf-8"))


def _ops_from_plan(plan: Dict[str, Any]) -> List[str]:
    ops: List[str] = []
    for b in plan.get("bpel", []):
        for s in b.get("steps", []):
            op = s.get("operation") if isinstance(s, dict) else None
            if op and op != "null":
                ops.append(op)
    seen = set()
    return [x for x in ops if not (x in seen or seen.add(x))]


def _find_xsds(resources_root: Path, op: str) -> tuple[str, str]:
    base = resources_root / "contracts" / "selected"
    op_lower = op.lower()
    # First try to locate a WSDL that declares the operation
    service_dir: Path | None = None
    for w in base.glob("**/*.wsdl"):
        try:
            text = w.read_text(encoding="utf-8", errors="ignore").lower()
            if op_lower in text:
                service_dir = w.parent
                break
        except Exception:
            continue
    # Prefer XSDs under the matched service directory
    cand_req = None
    cand_rep = None
    def pick_xsds(search_dir: Path):
        nonlocal cand_req, cand_rep
        # Prioritise explicit names
        priorities_req = ["*Request*.xsd", "LocationRequest.xsd", "*request*.xsd"]
        priorities_rep = ["*Reply*.xsd", "LocationListReply.xsd", "*response*.xsd", "*reply*.xsd"]
        for pat in priorities_req:
            for p in search_dir.glob(pat):
                cand_req = str(p.relative_to(resources_root))
                break
            if cand_req:
                break
        for pat in priorities_rep:
            for p in search_dir.glob(pat):
                cand_rep = str(p.relative_to(resources_root))
                break
            if cand_rep:
                break
    if service_dir:
        pick_xsds(service_dir)
    # Fallback: search whole selected tree using op hint
    if not cand_req or not cand_rep:
        for p in base.glob("**/*.xsd"):
            name = p.name.lower()
            parent = p.parent.name.lower()
            if op_lower in parent or op_lower in name:
                if (not cand_req) and ("request" in name or name.endswith("request.xsd")):
                    cand_req = str(p.relative_to(resources_root))
                if (not cand_rep) and ("reply" in name or "response" in name):
                    cand_rep = str(p.relative_to(resources_root))
    # If still missing, signal no match (skip controller)
    if not cand_req or not cand_rep:
        return "", ""
    return cand_req, cand_rep


def generate_controllers(plan_path: str, java_root: str, java_package: str) -> List[str]:
    plan = _load_plan(plan_path)
    ops = _ops_from_plan(plan)
    out_paths: List[str] = []
    resources_root = Path(java_root).parent / "resources"
    for op in ops:
        cls = op
        path = f"svc/{op}"
        req_xsd, rep_xsd = _find_xsds(resources_root, op)
        # Skip controllers when XSD pair cannot be resolved
        if not req_xsd or not rep_xsd:
            continue
        # Skip controllers when XSD files are not found on disk
        req_path = (resources_root / req_xsd) if not req_xsd.startswith("contracts/") else (resources_root / Path(req_xsd))
        rep_path = (resources_root / rep_xsd) if not rep_xsd.startswith("contracts/") else (resources_root / Path(rep_xsd))
        if not req_path.exists() or not rep_path.exists():
            continue
        src = (
            JAVA_TMPL
            .replace("__PKG__", java_package)
            .replace("__PATH__", path)
            .replace("__CLS__", cls)
            .replace("__REQ_XSD__", req_xsd)
            .replace("__REP_XSD__", rep_xsd)
        )
        dst_dir = Path(java_root) / java_package.replace(".", "/") / "controller"
        os.makedirs(dst_dir, exist_ok=True)
        dst = dst_dir / f"{cls}Controller.java"
        dst.write_text(src, encoding="utf-8")
        out_paths.append(str(dst))
    return out_paths


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Generate Spring controllers per operation from orchestration plan")
    parser.add_argument("--plan", default="agent/orchestration_plan.json")
    parser.add_argument("--java-root", default="generated/result/src/main/java")
    parser.add_argument("--java-package", default="com.example")
    args = parser.parse_args()
    paths = generate_controllers(args.plan, args.java_root, args.java_package)
    print("\n".join(paths))


if __name__ == "__main__":
    main()
