import os
from pathlib import Path

from agent.src.controller_generator import generate_controllers


def test_generate_controllers_from_plan(tmp_path: Path):
    plan = Path("agent/orchestration_plan.json")
    assert plan.exists()
    java_root = tmp_path / "src/main/java"
    paths = generate_controllers(str(plan), str(java_root), "com.example")
    assert paths
    for p in paths:
        text = Path(p).read_text(encoding="utf-8")
        assert "@RestController" in text
        assert "@RequestMapping(path = \"/loc/" in text

