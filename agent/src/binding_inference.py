import json
import os
from pathlib import Path
from typing import Dict, Any, List


def infer_controller_path(operation: str) -> str:
    return f"loc/{operation}"


def resolve_route_placeholders(yaml_path: str, controller_path: str, request_xslt: str, reply_xslt: str, provider_uri: str) -> None:
    text = Path(yaml_path).read_text(encoding="utf-8")
    lines = text.splitlines()
    new_lines: List[str] = []
    current_op: str | None = None
    for ln in lines:
        s = ln.strip()
        if "platform-http:/{{controller_path}}" in s:
            indent = ln.split("uri:")[0] if "uri:" in ln else ln[:ln.find(s)]
            new_lines.append(f"{indent}uri: platform-http:/{controller_path}")
            continue
        if s.startswith("id:") and "-controller" in s:
            # id: loc-service-Op-controller
            parts = s.split()
            rid = parts[-1]
            try:
                op = rid.split("-")[-2]
            except Exception:
                op = None
            current_op = op
            new_lines.append(ln)
            continue
        if current_op and s.startswith("uri:") and "platform-http:" in s:
            indent = ln.split("uri:")[0]
            new_lines.append(f"{indent}uri: platform-http:/loc/{current_op}")
            continue
        # generic replacements
        if "{{request_xslt}}" in ln:
            ln = ln.replace("{{request_xslt}}", request_xslt)
        if "{{reply_xslt}}" in ln:
            ln = ln.replace("{{reply_xslt}}", reply_xslt)
        if "{{provider.uri}}" in ln or "{{provider_uri}}" in ln or "{{{{provider_uri}}}}" in ln:
            if current_op:
                prov_local = f"http://localhost:8081/svc/{current_op}?bridgeEndpoint=true"
                ln = ln.replace("{{provider.uri}}", prov_local)
                ln = ln.replace("{{provider_uri}}", prov_local)
                ln = ln.replace("{{{{provider_uri}}}}", prov_local)
            else:
                ln = ln.replace("{{provider.uri}}", provider_uri)
                ln = ln.replace("{{provider_uri}}", provider_uri)
                ln = ln.replace("{{{{provider_uri}}}}", provider_uri)
        if "resourceUri: {{classpath:" in ln:
            ln = ln.replace("resourceUri: {{classpath:", "resourceUri: classpath:")
        if ln.strip().startswith("resourceUri:") and ln.strip().endswith("}}"):
            ln = ln.rstrip("}")
        if s.startswith("uri:"):
            parts = ln.split("uri:", 1)
            val = parts[1].strip()
            if not (val.startswith('"') and val.endswith('"')):
                ln = parts[0] + "uri: \"" + val + "\""
            # unwrap accidental double-braced http URIs such as {{http://...}}
            v = ln.split("uri:", 1)[1].strip().strip('"')
            if v.startswith("{{http"):
                v2 = v.replace("{{http", "http")
                v2 = v2.replace("}}?", "?")
                ln = parts[0] + "uri: \"" + v2 + "\""
        # normalise excessive braces e.g., {{{{x}}}} -> {{x}}
        if "{{{{" in ln or "}}}}" in ln:
            ln = ln.replace("{{{{", "{{").replace("}}}}", "}}")
        # fix duplicated '?' when appending options
        if "?bridgeEndpoint=true?connectTimeout" in ln:
            ln = ln.replace("?bridgeEndpoint=true?connectTimeout", "?bridgeEndpoint=true&connectTimeout")
        if "platform-http:/loc/service}}" in ln:
            ln = ln.replace("platform-http:/loc/service}}", "platform-http:/loc/service")
        new_lines.append(ln)
    Path(yaml_path).write_text("\n".join(new_lines), encoding="utf-8")


def resolve_generated_routes(routes_dir: str, resources_root: str) -> List[str]:
    resolved: List[str] = []
    for p in Path(routes_dir).glob("*.yaml"):
        name = p.stem
        op = name.split("-")[-1]
        ctrl = infer_controller_path(op)
        req = "classpath:xslt/identity.xsl"
        rep = "classpath:xslt/identity.xsl"
        prov = f"http://localhost:8081/svc/{op}?bridgeEndpoint=true"
        resolve_route_placeholders(str(p), ctrl, req, rep, prov)
        resolved.append(str(p))
    return resolved


def main():
    import argparse
    parser = argparse.ArgumentParser(description="Binding inference and placeholder resolution for generated routes")
    parser.add_argument("--routes", default="generated/result/src/main/resources/routes")
    parser.add_argument("--resources-root", default="generated/result/src/main/resources")
    args = parser.parse_args()
    resolved = resolve_generated_routes(args.routes, args.resources_root)
    print(json.dumps({"resolved": resolved, "count": len(resolved)}))


if __name__ == "__main__":
    main()
