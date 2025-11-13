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

    manifest_path = inventory_main(deps_path, "agent/manifest.json")
    manifest = Path(manifest_path).read_text(encoding="utf-8")
    import json
    manifest_json = json.loads(manifest)

    plan = build_orchestration_plan(manifest_json)
    plan_path = Path("agent/orchestration_plan.json")
    plan_path.write_text(json.dumps(plan, indent=2), encoding="utf-8")

    scaffold_result(
        output_project,
        clean=clean,
        orchestration_plan_path=str(plan_path),
        service_name="loc-service",
    )

    assemble_contracts("agent/manifest.json", os.path.join(output_project, "src/main/resources/contracts/selected"), wsdl_filter=["LocationRetrievalLOC3X1M.wsdl"])

    # Resolve placeholders in routes
    routes_dir = os.path.join(output_project, "src/main/resources/routes")
    resources_root = os.path.join(output_project, "src/main/resources")
    resolve_generated_routes(routes_dir, resources_root)
    # Remove combined loc-service.yaml if present (use per-operation routes synthesized)
    try:
        p = Path(routes_dir) / "loc-service.yaml"
        if p.exists():
            p.unlink()
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
