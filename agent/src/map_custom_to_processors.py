import os
import re
import textwrap
import xml.etree.ElementTree as ET

MAP_NS = "http://www.ibm.com/xmlns/prod/websphere/wbiserver/map/6.0.0"
NS = {"map": MAP_NS}


class CustomBlock:
    def __init__(self, source_map_path: str, execution_order: int, input_property: str, output_property: str, java_code: str):
        self.source_map_path = source_map_path
        self.execution_order = execution_order
        self.input_property = input_property
        self.output_property = output_property
        self.java_code = java_code or ""
        self.operation = self._infer_operation(self.java_code)

    def _infer_operation(self, java_code: str) -> str:
        if "trim()" in java_code:
            return "trim"
        return "assign"


def discover_custom_blocks(file_path: str):
    tree = ET.parse(file_path)
    root = tree.getroot()
    blocks = []
    for prop_map in root.findall("map:propertyMap", NS):
        try:
            exec_order = int(prop_map.attrib.get("executionOrder", "0"))
        except ValueError:
            exec_order = 0
        custom_el = prop_map.find("map:custom", NS)
        if custom_el is None:
            continue
        input_el = custom_el.find("map:input", NS)
        output_el = custom_el.find("map:output", NS)
        code_el = custom_el.find("map:javaCode", NS)
        input_prop = input_el.attrib.get("property", "") if input_el is not None else ""
        output_prop = output_el.attrib.get("property", "") if output_el is not None else ""
        java_code = code_el.text if code_el is not None else ""
        blocks.append(CustomBlock(file_path, exec_order, input_prop, output_prop, java_code))
    return blocks


def _sanitize_identifier(name: str) -> str:
    return re.sub(r"[^A-Za-z0-9_]", "_", name)


def java_class_name_for_block(block: CustomBlock) -> str:
    base = _sanitize_identifier(block.output_property or block.input_property or "Custom")
    op = block.operation.capitalize()
    if not base:
        base = "Custom"
    return f"{base}{op}Processor"


def generate_java_code(block: CustomBlock) -> str:
    class_name = java_class_name_for_block(block)
    if block.operation == "trim":
        method_body = textwrap.dedent(
            """
            if (input == null) { return null; }
            return input.trim();
            """
        ).strip()
    else:
        method_body = textwrap.dedent(
            """
            return input;
            """
        ).strip()

    code = f"""
// Auto-generated from IBM map:custom block
public class {class_name} {{
    /**
     * Derived from map at: {block.source_map_path}
     * executionOrder={block.execution_order}
     * inputProperty={block.input_property}
     * outputProperty={block.output_property}
     * operation={block.operation}
     */
    public String process(String input) {{
        {method_body}
    }}
}}
"""
    return code.strip()


def write_processors(blocks, output_dir: str):
    os.makedirs(output_dir, exist_ok=True)
    outputs = []
    for block in blocks:
        class_name = java_class_name_for_block(block)
        java_code = generate_java_code(block)
        out_path = os.path.join(output_dir, f"{class_name}.java")
        with open(out_path, "w", encoding="utf-8") as f:
            f.write(java_code)
        outputs.append(out_path)
    return outputs


def rationale_for_block(block: CustomBlock) -> str:
    lines = [
        f"Source: {block.source_map_path}",
        f"Order: {block.execution_order}",
        f"Input: {block.input_property}",
        f"Output: {block.output_property}",
        f"Operation: {block.operation}",
    ]
    return "\n".join(lines)


def simulate_processor(block: CustomBlock, input_value: str):
    if block.operation == "trim":
        return None if input_value is None else input_value.strip()
    return input_value


__all__ = [
    "CustomBlock",
    "discover_custom_blocks",
    "java_class_name_for_block",
    "generate_java_code",
    "write_processors",
    "rationale_for_block",
    "simulate_processor",
]