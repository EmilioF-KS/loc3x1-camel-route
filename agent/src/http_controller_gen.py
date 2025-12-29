import os
import re
import time
from typing import Dict, List, Tuple


def _write(path: str, text: str) -> None:
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(text)


def _class_name_from_path(path: str) -> str:
    parts = [p for p in path.split("/") if p]
    out = []
    for p in parts:
        p = re.sub(r"[^A-Za-z0-9{}]+", "", p)
        if p.startswith("{") and p.endswith("}"):
            p = p[1:-1]
        out.append(p[:1].upper() + p[1:])
    base = "".join(out) or "Endpoint"
    if not base.endswith("Controller"):
        base += "Controller"
    return base


def _parse_verbs(block: str) -> List[str]:
    m = re.search(r"verbs\s*:\s*\[([^\]]+)\]", block, flags=re.IGNORECASE)
    if m:
        verbs = [v.strip().upper() for v in m.group(1).split(",")]
        return [v for v in verbs if v in {"GET", "POST", "PUT", "DELETE", "PATCH"}]
    # Multiline list
    verbs: List[str] = []
    for ln in block.splitlines():
        ln = ln.strip()
        mm = re.match(r"-\s*(GET|POST|PUT|DELETE|PATCH)\b", ln, flags=re.IGNORECASE)
        if mm:
            verbs.append(mm.group(1).upper())
    return verbs or ["POST"]


def extract_platform_http_endpoints(yaml_text: str) -> List[Tuple[str, List[str]]]:
    endpoints: List[Tuple[str, List[str]]] = []
    # Find 'from:' blocks and capture nearby uri and verbs
    # Simple heuristic: capture up to 20 lines after 'from:' occurrence
    lines = yaml_text.splitlines()
    for i, ln in enumerate(lines):
        if re.search(r"^\s*(?:-\s*)?from\s*:", ln):
            window = "\n".join(lines[i : min(len(lines), i + 20)])
            m = re.search(r"uri\s*:\s*platform-http\s*:\s*([^\s]+)", window)
            if not m:
                continue
            raw = m.group(1).strip()
            # Clean placeholders and quotes
            raw = raw.strip("'\"")
            raw = raw.replace("{{", "").replace("}}", "")
            raw = raw if raw.startswith("/") else ("/" + raw)
            verbs = _parse_verbs(window)
            endpoints.append((raw, verbs))
    # Deduplicate while preserving first verb set
    seen: Dict[str, List[str]] = {}
    for p, v in endpoints:
        if p not in seen:
            seen[p] = v
    return [(p, seen[p]) for p in seen]


def _java_for_endpoint(java_package: str, path: str, verbs: List[str], yaml_path: str) -> str:
    cls = _class_name_from_path(path)
    now = time.strftime("%Y-%m-%d %H:%M:%S")
    lines: List[str] = []
    lines.append("package " + java_package + ".controller.http;\n\n")
    lines.append("/**\n")
    lines.append(" * Source YAML: " + yaml_path.replace("\\", "/") + "\n")
    lines.append(" * Generated at: " + now + "\n")
    lines.append(" * Usage: Implement handlers to integrate with Camel routes.\n")
    lines.append(" * Steps: Validate input, route to Camel, marshal reply.\n")
    lines.append(" */\n")
    lines.append("import org.slf4j.Logger;\n")
    lines.append("import org.slf4j.LoggerFactory;\n")
    lines.append("import org.springframework.http.MediaType;\n")
    lines.append("import org.springframework.http.ResponseEntity;\n")
    lines.append("import org.springframework.web.bind.annotation.*;\n\n")
    lines.append("@RestController\n")
    lines.append("@RequestMapping(path = \"" + path + "\")\n")
    lines.append("public class " + cls + " {\n")
    lines.append("  private static final Logger log = LoggerFactory.getLogger(" + cls + ".class);\n\n")
    for verb in verbs:
        if verb == "GET":
            lines.append("  @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)\n")
            lines.append("  public ResponseEntity<String> get() {\n")
            lines.append("    try {\n")
            lines.append("      // TODO: implement GET handling\n")
            lines.append("      return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(\"<ok/>\");\n")
            lines.append("    } catch (Exception e) {\n")
            lines.append("      log.error(\"GET error: {}\", e.getMessage(), e);\n")
            lines.append("      return ResponseEntity.status(500).contentType(MediaType.APPLICATION_XML).body(\"<error>internal</error>\");\n")
            lines.append("    }\n")
            lines.append("  }\n\n")
        if verb == "POST":
            lines.append("  @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)\n")
            lines.append("  public ResponseEntity<String> post(@RequestBody String body) {\n")
            lines.append("    try {\n")
            lines.append("      // TODO: implement POST handling\n")
            lines.append("      return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(\"<ok/>\");\n")
            lines.append("    } catch (Exception e) {\n")
            lines.append("      log.error(\"POST error: {}\", e.getMessage(), e);\n")
            lines.append("      return ResponseEntity.status(500).contentType(MediaType.APPLICATION_XML).body(\"<error>internal</error>\");\n")
            lines.append("    }\n")
            lines.append("  }\n\n")
        if verb == "PUT":
            lines.append("  @PutMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)\n")
            lines.append("  public ResponseEntity<String> put(@RequestBody String body) {\n")
            lines.append("    try {\n")
            lines.append("      // TODO: implement PUT handling\n")
            lines.append("      return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(\"<ok/>\");\n")
            lines.append("    } catch (Exception e) {\n")
            lines.append("      log.error(\"PUT error: {}\", e.getMessage(), e);\n")
            lines.append("      return ResponseEntity.status(500).contentType(MediaType.APPLICATION_XML).body(\"<error>internal</error>\");\n")
            lines.append("    }\n")
            lines.append("  }\n\n")
        if verb == "DELETE":
            lines.append("  @DeleteMapping(produces = MediaType.APPLICATION_XML_VALUE)\n")
            lines.append("  public ResponseEntity<String> delete() {\n")
            lines.append("    try {\n")
            lines.append("      // TODO: implement DELETE handling\n")
            lines.append("      return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(\"<ok/>\");\n")
            lines.append("    } catch (Exception e) {\n")
            lines.append("      log.error(\"DELETE error: {}\", e.getMessage(), e);\n")
            lines.append("      return ResponseEntity.status(500).contentType(MediaType.APPLICATION_XML).body(\"<error>internal</error>\");\n")
            lines.append("    }\n")
            lines.append("  }\n\n")
    lines.append("}\n")
    return "".join(lines)


def generate_http_controllers(yaml_path: str, java_root: str, java_package: str, out_report: str | None = None) -> List[str]:
    text = open(yaml_path, "r", encoding="utf-8").read()
    eps = extract_platform_http_endpoints(text)
    created: List[str] = []
    duplicate_paths = []
    seen: Dict[str, bool] = {}
    for path, verbs in eps:
        if path in seen:
            duplicate_paths.append(path)
            continue
        seen[path] = True
        cls = _class_name_from_path(path)
        dst_dir = os.path.join(java_root, java_package.replace(".", "/"), "controller", "http")
        os.makedirs(dst_dir, exist_ok=True)
        dst = os.path.join(dst_dir, f"{cls}.java")
        src = _java_for_endpoint(java_package, path, verbs, yaml_path)
        _write(dst, src)
        created.append(dst)
    if out_report:
        lines = []
        lines.append("YAML: " + yaml_path.replace("\\", "/"))
        lines.append("Generated: " + str(len(created)))
        for c in created:
            lines.append("- " + c)
        if duplicate_paths:
            lines.append("Duplicates:")
            for p in duplicate_paths:
                lines.append("  - " + p)
        _write(out_report, "\n".join(lines))
    return created
