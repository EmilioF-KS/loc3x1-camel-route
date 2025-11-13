from pathlib import Path
from typing import List


def validate_camel_integration_yaml_text(text: str) -> None:
    required_headers = [
        "apiVersion: camel.apache.org/v1",
        "kind: Integration",
        "metadata:",
        "spec:",
        "flows:",
    ]
    for h in required_headers:
        if h not in text:
            raise ValueError(f"Missing required header: {h}")

    lines = [ln.rstrip() for ln in text.splitlines()]
    def has_block(prefix: str) -> bool:
        return any(ln.strip().startswith(prefix) for ln in lines)

    if not has_block("- route:"):
        raise ValueError("Missing route block")
    if not has_block("from:"):
        raise ValueError("Missing from block")
    if not has_block("steps:"):
        raise ValueError("Missing steps block")

    # Validate presence of at least one known Camel step
    known_steps = [
        "set-header",
        "set-body",
        "set-property",
        "log:",
        "xslt:",
        " to:",
        "choice:",
        "filter:",
        "enrich:",
        "aggregate:",
        "multicast:",
    ]
    if not any(k in text for k in known_steps):
        raise ValueError("No known Camel step found")

    # Validate error handler
    if not has_block("- on-exception:"):
        raise ValueError("Missing on-exception block")
    if not has_block("handled: true"):
        raise ValueError("on-exception must be handled")
    if "CamelHttpResponseCode" not in text:
        raise ValueError("on-exception must set CamelHttpResponseCode")


def validate_camel_integration_yaml_file(path: str) -> None:
    text = Path(path).read_text(encoding="utf-8")
    validate_camel_integration_yaml_text(text)
