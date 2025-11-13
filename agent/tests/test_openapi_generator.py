import os
from pathlib import Path

from agent.src.openapi_generator import generate_openapi


def test_generate_openapi_contains_ops(tmp_path: Path):
    plan = Path("agent/orchestration_plan.json")
    out = tmp_path / "openapi.yaml"
    path = generate_openapi(str(plan), str(out), title="loc3x1")
    assert os.path.isfile(path)
    text = Path(path).read_text(encoding="utf-8")
    assert text.startswith("openapi: 3.0.3")
    assert "/loc/" in text

