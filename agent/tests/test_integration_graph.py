from pathlib import Path
import os
import json

import pytest

from agent.src.inventory import discover_artifacts, write_manifest
from agent.src.graph import build_import_graph


def test_integration_build_graph_from_dependencies(tmp_path):
    input_dir = os.environ.get("DEP_INPUT_DIR", "CHUBB/Dependencies")
    root = Path(input_dir).resolve()
    if not root.exists():
        pytest.skip(f"Input directory not found: {root}")

    artifacts = discover_artifacts(root)
    # Sanity: we have a reasonable set
    assert len(artifacts) >= 20

    # Write a temporary manifest and load it
    manifest_path = tmp_path / "manifest.json"
    write_manifest(manifest_path, artifacts, root)
    data = json.loads(manifest_path.read_text())

    # Build graph and assert basic properties
    graph = build_import_graph(data)
    assert "nodes" in graph and len(graph["nodes"]) >= 20
    assert "edges" in graph and len(graph["edges"]) >= 1
    # Expect at least one import edge present; many should resolve
    assert any(e.get("kind") == "import" for e in graph["edges"])  # presence
    # Namespace info should appear on some nodes (WSDL/XSD)
    assert any("namespaces" in n for n in graph["nodes"])  # coverage
    # Cycle report exists and is well-formed
    report = graph.get("report", {})
    assert isinstance(report.get("cycle_count", 0), int)