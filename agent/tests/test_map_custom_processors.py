import os
from agent.src.map_custom_to_processors import (
    discover_custom_blocks,
    java_class_name_for_block,
    generate_java_code,
    write_processors,
    simulate_processor,
)


def test_discovery_parses_real_custom_blocks():
    path = os.path.join(
        "CHUBB",
        "00-Emilio-Logic",
        "a7",
        "RandLocationToCimLocationMap 1.xml",
    )
    blocks = discover_custom_blocks(path)
    # Expect multiple custom blocks present in the real map
    assert len(blocks) >= 5
    # First block should be a trim operation
    assert blocks[0].operation in ("trim", "assign")


def test_java_generation_contains_expected_methods():
    path = os.path.join(
        "CHUBB",
        "00-Emilio-Logic",
        "a7",
        "RandLocationToCimLocationMap 1.xml",
    )
    blocks = discover_custom_blocks(path)
    block = blocks[0]
    code = generate_java_code(block)
    # Class name derived from output property
    cls_name = java_class_name_for_block(block)
    assert f"class {cls_name}" in code
    # Trim operation should include .trim()
    if block.operation == "trim":
        assert ".trim()" in code


def test_write_processors_outputs_files(tmp_path):
    path = os.path.join(
        "CHUBB",
        "00-Emilio-Logic",
        "a7",
        "RandLocationToCimLocationMap 1.xml",
    )
    blocks = discover_custom_blocks(path)
    out_dir = tmp_path / "java_processors"
    outputs = write_processors(blocks[:3], str(out_dir))
    assert len(outputs) == 3
    for fp in outputs:
        assert os.path.exists(fp)


def test_golden_logic_python_simulation():
    path = os.path.join(
        "CHUBB",
        "00-Emilio-Logic",
        "a7",
        "RandLocationToCimLocationMap 1.xml",
    )
    blocks = discover_custom_blocks(path)
    # Find a trim block
    trim_block = next((b for b in blocks if b.operation == "trim"), None)
    assert trim_block is not None
    assert simulate_processor(trim_block, "  NYC  ") == "NYC"
    assert simulate_processor(trim_block, None) is None