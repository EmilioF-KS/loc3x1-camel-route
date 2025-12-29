import argparse
import os
import sys
import shutil
from pathlib import Path


def run_one_click(deps_path: str, output_project: str, clean: bool = False, build_run: bool = False) -> None:
    from agent.src.inventory import main as inventory_main
    from agent.src.orchestration import build_orchestration_plan
    from agent.src.codegen_inputs import assemble_contracts
    from agent.src.scaffold import scaffold_result
    from agent.src.binding_inference import resolve_generated_routes
    from agent.src.map_move_to_xslt import convert_file

    manifest_path = inventory_main(deps_path, "agent/manifest.json")
    manifest = Path(manifest_path).read_text(encoding="utf-8")
    import json
    manifest_json = json.loads(manifest)

    plan = build_orchestration_plan(manifest_json)
    plan_path = Path("agent/orchestration_plan.json")
    plan_path.write_text(json.dumps(plan, indent=2), encoding="utf-8")

    maps_candidate = os.path.join(deps_path, "Maps")
    maps_dir = maps_candidate if os.path.isdir(maps_candidate) else None
    scaffold_result(
        output_project,
        clean=clean,
        maps_dir=maps_dir,
        orchestration_plan_path=str(plan_path),
        service_name="loc-service",
        include_controllers=False,
    )

    assemble_contracts("agent/manifest.json", os.path.join(output_project, "src/main/resources/contracts/selected"))

    # Synthesize a single aggregate YAML under routes
    from agent.src.route_synthesis import synthesize_routes
    routes_dir = os.path.join(output_project, "src/main/resources/routes")
    resources_root = os.path.join(output_project, "src/main/resources")
    # Determine generated operations by scanning controllers
    java_ctrl_dir = Path(output_project) / "src/main/java/com/example/controller"
    ops_generated: list[str] = []
    if java_ctrl_dir.exists():
        for p in java_ctrl_dir.glob("*Controller.java"):
            name = p.stem.replace("Controller", "")
            ops_generated.append(name)
    synthesize_routes(str(plan_path), routes_dir, service_name="loc-service", allowed_ops=ops_generated)
    # Resolve placeholders in the generated YAML(s)
    resolve_generated_routes(routes_dir, resources_root)
    # Also emit a single YAML file into the CHUBB orchestration project folder for reference/delivery
    try:
        chubb_out_dir = Path("CHUBB/00-Emilio-Logic/a9.location-orchestration-rest/src/main/java/com/example/location/routes")
        chubb_out_dir.mkdir(parents=True, exist_ok=True)
        src_yaml = Path(routes_dir) / "loc-service.yaml"
        if src_yaml.exists():
            (chubb_out_dir / "LocationRoutes.yaml").write_text(src_yaml.read_text(encoding="utf-8"), encoding="utf-8")
    except Exception:
        pass

    # Strict maps conversion only from the provided Dependencies path
    try:
        xslt_dir = os.path.join(output_project, "src/main/resources/xslt")
        os.makedirs(xslt_dir, exist_ok=True)
        generated_xslts = []
        for root, _, files in os.walk(deps_path):
            for name in files:
                if name.lower().endswith('.xml') and 'map' in name.lower():
                    src = os.path.join(root, name)
                    out = convert_file(src, xslt_dir)
                    generated_xslts.append(out)
        # Regenerate mediation routes to include all XSLTs found
        try:
            java_root = os.path.join(output_project, "src/main/java")
            pkg = "com.example"
            dst_dir = os.path.join(java_root, pkg.replace('.', '/'), "mediation")
            os.makedirs(dst_dir, exist_ok=True)
            lines = []
            lines.append(f"package {pkg}.mediation;\n")
            lines.append("import org.apache.camel.builder.RouteBuilder;\n")
            lines.append("import org.springframework.stereotype.Component;\n\n")
            lines.append("@Component\n")
            lines.append("public class MediationRoutes extends RouteBuilder {\n")
            lines.append("  @Override\n  public void configure() throws Exception {\n")
            lines.append("    from(\"direct:mediation/identity\").to(\"xslt:classpath:xslt/identity.xsl\");\n")
            for p in generated_xslts:
                name = os.path.basename(p)
                base = os.path.splitext(name)[0]
                lines.append(f"    from(\"direct:mediation/{base}\").to(\"xslt:classpath:xslt/{name}\");\n")
            lines.append("  }\n")
            lines.append("}\n")
            dst = os.path.join(dst_dir, "MediationRoutes.java")
            with open(dst, 'w', encoding='utf-8') as f:
                f.write("".join(lines))
        except Exception:
            pass
    except Exception:
        # maps conversion is best-effort; continue if none present
        pass

    # Generate controllers after contracts are assembled so XSD resolution is accurate
    try:
        from agent.src.controller_generator import generate_controllers
        java_root = os.path.join(output_project, "src/main/java")
        generate_controllers(str(plan_path), java_root, "com.example")
    except Exception:
        pass

    # Generate minimal sample request XMLs per operation using the resolved Request XSD
    try:
        from agent.src.controller_generator import _find_xsds
        resources_root = Path(output_project) / "src/main/resources"
        samples_dir = Path(output_project) / "samples"
        samples_dir.mkdir(parents=True, exist_ok=True)
        ctrl_dir = Path(output_project) / "src/main/java/com/example/controller"
        ops_generated: list[str] = []
        if ctrl_dir.exists():
            for p in ctrl_dir.glob("*Controller.java"):
                name = p.stem.replace("Controller", "")
                ops_generated.append(name)
        import re
        for op in ops_generated:
            req_xsd, _ = _find_xsds(resources_root, op)
            if not req_xsd:
                continue
            xsd_path = resources_root / req_xsd
            try:
                xsd_text = xsd_path.read_text(encoding="utf-8", errors="ignore")
                m = re.search(r"<\s*xs:element\s+name=\"([^\"]+)\"", xsd_text)
                root = m.group(1) if m else f"{op}Request"
                xml = f"<?xml version=\"1.0\" encoding=\"UTF-8\"?><{root}/>"
                (samples_dir / f"{op}-request-min.xml").write_text(xml, encoding="utf-8")
            except Exception:
                pass
    except Exception:
        pass

    if build_run:
        import subprocess
        mvn = shutil.which("mvn")
        if not mvn:
            raise RuntimeError("Maven not found for build_run")
        subprocess.check_call([mvn, "-q", "-DskipTests", "package"], cwd=output_project)
        # Find jar
        target = Path(output_project) / "target"
        jars = [p for p in target.glob("*.jar")]
        if not jars:
            raise RuntimeError("No jar produced")
        jar = str(jars[0])
        # Run with provider stub override
        proc = subprocess.Popen([
            "java", "-jar", jar, "--server.port=8081", "--provider.uri=http://localhost:8081/provider/locations"
        ], cwd=output_project, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
        try:
            import time, urllib.request
            # Wait for actuator health
            ok = False
            for _ in range(120):
                time.sleep(1.0)
                try:
                    with urllib.request.urlopen("http://localhost:8081/actuator/health", timeout=0.5) as resp:
                        if resp.status == 200:
                            ok = True
                            break
                except Exception:
                    pass
            if not ok:
                raise RuntimeError("Service did not become healthy")
        finally:
            proc.terminate()


def main():
    parser = argparse.ArgumentParser(description="One-click generator: Dependencies → runnable open project")
    parser.add_argument("--input", default="CHUBB/Dependencies")
    parser.add_argument("--output", default="generated/result")
    parser.add_argument("--clean", action="store_true")
    parser.add_argument("--build-run", action="store_true")
    args = parser.parse_args()
    run_one_click(args.input, args.output, clean=args.clean, build_run=args.build_run)
    print(f"Generated project at {args.output}")


if __name__ == "__main__":
    main()
