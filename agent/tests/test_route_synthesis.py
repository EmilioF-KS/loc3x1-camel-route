import os
from pathlib import Path

from agent.src.route_synthesis import synthesize_routes, synthesize_routes_split, validate_route_yaml


def test_route_synthesis_generates_yaml_without_hardcoded_paths(tmp_path: Path):
    plan_file = tmp_path / "plan.json"
    plan_file.write_text("{}", encoding="utf-8")

    out_dir = tmp_path / "routes"
    out_path = synthesize_routes(str(plan_file), str(out_dir), service_name="generic-service")

    assert os.path.exists(out_path)
    text = Path(out_path).read_text(encoding="utf-8")

    # Structural checks: Integration kind, flows, route, on-exception
    assert "apiVersion: camel.apache.org/v1" in text
    assert "kind: Integration" in text
    assert "spec:" in text and "flows:" in text
    assert "- route:" in text and "from:" in text and "steps:" in text
    assert "- on-exception:" in text and "handled: true" in text

    # Placeholder checks to ensure no hardcoded paths
    assert "{{controller_path}}" in text
    assert "{{request_xslt}}" in text
    assert "{{provider_uri}}" in text
    assert "{{reply_xslt}}" in text

    # Ensure service name is reflected but paths are placeholders
    assert "generic-service-controller" in text
    assert "platform-http:" in text and "{{controller_path}}" in text


def test_route_synthesis_multiple_routes_from_plan(tmp_path: Path):
    plan_file = tmp_path / "plan.json"
    plan_file.write_text(
        (
            '{"bpel":[{"steps":[{"kind":"invoke","operation":"OpA"},'
            '{"kind":"invoke","operation":"OpB"},{"kind":"invoke","operation":"null"}]}]}'
        ),
        encoding="utf-8",
    )

    out_dir = tmp_path / "routes"
    out_path = synthesize_routes(str(plan_file), str(out_dir), service_name="svc")
    text = Path(out_path).read_text(encoding="utf-8")
    assert "svc-OpA-controller" in text
    assert "svc-OpB-controller" in text


def test_route_yaml_placeholder_validator(tmp_path: Path):
    yaml_file = tmp_path / "svc.yaml"
    yaml_file.write_text(
        (
            "apiVersion: camel.apache.org/v1\nkind: Integration\nmetadata:\n  name: svc\n"
            "spec:\n  flows:\n    - route:\n        id: svc-controller\n        from:\n          uri: platform-http:/{{controller_path}}\n        steps:\n          - xslt:\n              resourceUri: {{request_xslt}}\n          - to:\n              uri: {{provider_uri}}\n          - xslt:\n              resourceUri: {{reply_xslt}}\n"
        ),
        encoding="utf-8",
    )

    # Unresolved mode should pass (placeholders present)
    validate_route_yaml(str(yaml_file), mode="unresolved")

    # Replace placeholders and validate resolved mode
    text = yaml_file.read_text(encoding="utf-8")
    text = (
        text.replace("{{controller_path}}", "loc")
        .replace("{{request_xslt}}", "classpath:request.xsl")
        .replace("{{provider_uri}}", "http://provider")
        .replace("{{reply_xslt}}", "classpath:reply.xsl")
    )
    yaml_file.write_text(text, encoding="utf-8")
    validate_route_yaml(str(yaml_file), mode="resolved")


def test_route_yaml_validates_classpath_and_existence(tmp_path: Path):
    # Create a route YAML with classpath resources
    yaml_file = tmp_path / "svc.yaml"
    yaml_file.write_text(
        (
            "apiVersion: camel.apache.org/v1\nkind: Integration\nmetadata:\n  name: svc\n"
            "spec:\n  flows:\n    - route:\n        id: svc-controller\n        from:\n          uri: platform-http:/loc\n        steps:\n          - xslt:\n              resourceUri: classpath:xsl/exists.xsl\n          - to:\n              uri: http://provider\n          - xslt:\n              resourceUri: classpath:xsl/missing.xsl\n"
        ),
        encoding="utf-8",
    )
    # Create resources root and one existing XSLT
    resources_root = tmp_path / "resources"
    xslt_dir = resources_root / "xsl"
    xslt_dir.mkdir(parents=True, exist_ok=True)
    (xslt_dir / "exists.xsl").write_text("<xsl:stylesheet version='1.0'></xsl:stylesheet>", encoding="utf-8")
    # Validation should fail because one resource is missing
    try:
        validate_route_yaml(str(yaml_file), mode="resolved", resources_root=str(resources_root))
        assert False, "Expected validation to fail due to missing XSLT"
    except ValueError as e:
        assert "resource not found" in str(e)


def test_route_synthesis_split_per_operation(tmp_path: Path):
    plan_file = tmp_path / "plan.json"
    plan_file.write_text(
        (
            '{"bpel":[{"steps":[{"kind":"invoke","operation":"OpX"},'
            '{"kind":"invoke","operation":"OpY"}]}]}'
        ),
        encoding="utf-8",
    )

    out_dir = tmp_path / "routes"
    paths = synthesize_routes_split(str(plan_file), str(out_dir), service_name="svc")
    assert len(paths) == 2
    names = [Path(p).name for p in paths]
    assert "svc-OpX.yaml" in names
    assert "svc-OpY.yaml" in names
    # Check each file contains placeholders and specific route id
    for p in paths:
        t = Path(p).read_text(encoding="utf-8")
        assert "{{controller_path}}" in t
        assert "{{request_xslt}}" in t
        assert "{{provider_uri}}" in t
        assert "{{reply_xslt}}" in t


def test_generation_with_filled_placeholders(tmp_path: Path):
    plan_file = tmp_path / "plan.json"
    plan_file.write_text('{"bpel":[{"steps":[{"kind":"invoke","operation":"OpA"}]}]}', encoding="utf-8")

    out_dir = tmp_path / "routes"
    out_path = synthesize_routes(
        str(plan_file),
        str(out_dir),
        service_name="svc",
        controller_path="loc/opA",
        request_xslt="classpath:request.xsl",
        reply_xslt="classpath:reply.xsl",
        provider_uri="http://provider/opA",
    )
    validate_route_yaml(str(out_path), mode="resolved")
    text = Path(out_path).read_text(encoding="utf-8")
    assert "platform-http:/loc/opA" in text
    assert "classpath:request.xsl" in text
    assert "classpath:reply.xsl" in text
    assert "http://provider/opA" in text
    assert "connectTimeout={{provider.timeoutMs}}" in text
    assert "socketTimeout={{provider.timeoutMs}}" in text


def test_provider_wiring_with_properties(tmp_path: Path):
    plan_file = tmp_path / "plan.json"
    plan_file.write_text('{}'.strip(), encoding="utf-8")

    out_dir = tmp_path / "routes"
    out_path = synthesize_routes(
        str(plan_file),
        str(out_dir),
        service_name="svc",
        controller_path="loc/op",
        request_xslt="classpath:xsl/req.xsl",
        reply_xslt="classpath:xsl/rep.xsl",
        provider_uri="{{provider.uri}}",
    )
    text = Path(out_path).read_text(encoding="utf-8")
    assert "{{provider.uri}}?connectTimeout={{provider.timeoutMs}}&socketTimeout={{provider.timeoutMs}}" in text
    assert "redelivery-policy:" in text
    assert "maximumRedeliveries: {{provider.retry.maxAttempts}}" in text
    assert "redeliveryDelay: {{provider.retry.delayMs}}" in text


def test_gen104_correlation_header_and_request_logging(tmp_path: Path):
    plan_file = tmp_path / "plan.json"
    plan_file.write_text("{}", encoding="utf-8")

    out_dir = tmp_path / "routes"
    out_path = synthesize_routes(
        str(plan_file),
        str(out_dir),
        service_name="svc",
        controller_path="loc/op",
        request_xslt="classpath:xsl/req.xsl",
        reply_xslt="classpath:xsl/rep.xsl",
        provider_uri="http://provider/op",
    )
    text = Path(out_path).read_text(encoding="utf-8")
    # Correlation ID header is set from exchange id
    assert "name: X-Correlation-ID" in text
    assert "simple: \"${exchangeId}\"" in text
    # Request logging uses correlation ID
    assert "message: \"[${header.X-Correlation-ID}] Request received\"" in text
    assert "level: INFO" in text


def test_gen104_on_exception_maps_to_http_500_and_logs(tmp_path: Path):
    plan_file = tmp_path / "plan.json"
    plan_file.write_text("{}", encoding="utf-8")

    out_dir = tmp_path / "routes"
    out_path = synthesize_routes(str(plan_file), str(out_dir), service_name="svc")
    text = Path(out_path).read_text(encoding="utf-8")
    # Error handling block sets HTTP response code
    assert "- on-exception:" in text and "handled: true" in text
    assert "name: CamelHttpResponseCode" in text
    assert "constant: 500" in text
    # Error body and logging with correlation ID
    assert "set-body:" in text and "<Error>Internal Server Error</Error>" in text
    assert "message: \"[${header.X-Correlation-ID}] Error handled: ${exception.message}\"" in text
    assert "level: ERROR" in text
