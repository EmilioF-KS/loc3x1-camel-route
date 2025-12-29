import os
from agent.src.scaffold import scaffold_result
from pathlib import Path


def test_scaffold_result_recreates_structure_and_synthesizes_routes(tmp_path):
    out_path = "generated/result"
    created = scaffold_result(
        out_path,
        client_input_path=None,
        clean=True,
        orchestration_plan_path="agent/orchestration_plan.json",
        service_name="loc-service",
    )
    # Core files exist
    assert os.path.isfile(os.path.join(out_path, "pom.xml"))
    assert os.path.isfile(os.path.join(out_path, "README.md"))
    assert os.path.isfile(os.path.join(out_path, "src/main/resources/application.yaml"))
    assert os.path.isfile(os.path.join(out_path, "src/main/resources/xsl/identity.xsl"))
    # Synthesized routes exist
    routes_dir = Path(os.path.join(out_path, "src/main/resources/routes"))
    assert routes_dir.is_dir()
    synthesized = list(routes_dir.glob("*.yaml"))
    assert synthesized, "Expected at least one synthesized route file"
    # Provider stub exists by default
    provider_stub = os.path.join(out_path, "src/main/java/com/example/controller/provider/ProviderStubController.java")
    assert os.path.isfile(provider_stub)
    # When provider_uri omitted, scaffold should use property-driven wiring with timeouts
    with open(str(synthesized[0]), "r", encoding="utf-8") as f:
        t0 = f.read()
    assert "{{provider.uri}}?connectTimeout={{provider.timeoutMs}}&socketTimeout={{provider.timeoutMs}}" in t0
    # Validate no unresolved placeholders if we provide explicit values
    created2 = scaffold_result(
        out_path,
        client_input_path=None,
        clean=True,
        orchestration_plan_path="agent/orchestration_plan.json",
        service_name="loc-service",
        controller_path="loc/getLocationList",
        request_xslt="classpath:xsl/identity.xsl",
        reply_xslt="classpath:xsl/identity.xsl",
        provider_uri="http://provider/locations",
    )
    synthesized2 = list(routes_dir.glob("*.yaml"))
    assert synthesized2, "Expected synthesized route after placeholder filling"
    # Pick one file and assert placeholders resolved
    with open(str(synthesized2[0]), "r", encoding="utf-8") as f:
        text = f.read()
    assert "{{controller_path}}" not in text
    assert "{{request_xslt}}" not in text
    assert "{{reply_xslt}}" not in text
    assert "{{provider_uri}}" not in text
    # Validate resolved mode and existence of classpath resources
    from agent.src.route_synthesis import validate_route_yaml
    resources_root = os.path.join(out_path, "src/main/resources")
    validate_route_yaml(str(synthesized2[0]), mode="resolved", resources_root=resources_root)
    # Trace file records client input path
    cip = os.path.join(out_path, "CLIENT_INPUT_PATH.txt")
    assert os.path.isfile(cip)
    with open(cip, "r", encoding="utf-8") as f:
        assert "NONE" in f.read()
