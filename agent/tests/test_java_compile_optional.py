import os
import pytest
from agent.src.map_custom_to_processors import discover_custom_blocks, write_processors
from agent.src.java_compile import javac_available, compile_java_files


@pytest.mark.skipif(not javac_available(), reason="javac not available in environment")
def test_generated_processors_compile_with_javac(tmp_path):
    path = os.path.join(
        "CHUBB",
        "00-Emilio-Logic",
        "a7",
        "RandLocationToCimLocationMap 1.xml",
    )
    blocks = discover_custom_blocks(path)
    out_dir = tmp_path / "java_processors"
    outputs = write_processors(blocks[:5], str(out_dir))
    success, output = compile_java_files(outputs, output_dir=str(tmp_path / "classes"))
    assert success, f"javac failed: {output}"