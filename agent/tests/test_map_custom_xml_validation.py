import xml.etree.ElementTree as ET
import os
from agent.src.map_custom_to_processors import discover_custom_blocks, simulate_processor


def test_processor_output_validated_against_expected_xml_minimal():
    # Build minimal input XML fragment with whitespace around CityName
    input_xml = ET.Element("Location")
    std_addr = ET.SubElement(input_xml, "StandardizedAddress")
    city_el = ET.SubElement(std_addr, "CityName")
    city_el.text = "  NYC  "

    # Discover a trim block to apply
    path = os.path.join(
        "CHUBB",
        "00-Emilio-Logic",
        "a7",
        "RandLocationToCimLocationMap 1.xml",
    )
    blocks = discover_custom_blocks(path)
    trim_block = next((b for b in blocks if b.operation == "trim"), None)
    assert trim_block is not None

    # Apply simulated processor
    trimmed = simulate_processor(trim_block, city_el.text)

    # Build minimal expected output XML and compare value
    output_xml = ET.Element("Location")
    out_std_addr = ET.SubElement(output_xml, "StandardizedAddress")
    out_city_el = ET.SubElement(out_std_addr, "CityName")
    out_city_el.text = trimmed

    assert out_city_el.text == "NYC"