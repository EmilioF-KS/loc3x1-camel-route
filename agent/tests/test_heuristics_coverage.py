import os
from lxml import etree
from agent.src.map_synthesis import synthesize_moves, synthesize_mapping_details


def _write_xsd(path: str, content: bytes):
    with open(path, 'wb') as f:
        f.write(content)


def test_heuristics_improve_coverage(tmp_path):
    # Create synthetic source/destination XSDs with near-name differences
    src_xsd_path = tmp_path / 'src.xsd'
    dst_xsd_path = tmp_path / 'dst.xsd'
    _write_xsd(str(src_xsd_path), b'''<?xml version="1.0"?>
    <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" targetNamespace="http://test/src" xmlns:tns="http://test/src">
      <xsd:complexType name="Root">
        <xsd:sequence>
          <xsd:element name="PostalCode" type="xsd:string"/>
          <xsd:element name="CityName" type="xsd:string"/>
          <xsd:element name="CountryCode" type="xsd:string"/>
        </xsd:sequence>
      </xsd:complexType>
    </xsd:schema>''')
    _write_xsd(str(dst_xsd_path), b'''<?xml version="1.0"?>
    <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" targetNamespace="http://test/dst" xmlns:tns="http://test/dst">
      <xsd:complexType name="Root">
        <xsd:sequence>
          <xsd:element name="PostCode" type="xsd:string"/>
          <xsd:element name="City" type="xsd:string"/>
          <xsd:element name="CountryCode" type="xsd:string"/>
        </xsd:sequence>
      </xsd:complexType>
    </xsd:schema>''')

    # Deterministic baseline (identical names only)
    moves_baseline = synthesize_moves(str(src_xsd_path), 'Root', str(dst_xsd_path), 'Root', 'In/Root', 'Out/Root', enable_heuristics=False)
    assert len(moves_baseline) == 1  # CountryCode only

    # Heuristics enabled, lower threshold to catch City/CityName and PostCode/PostalCode
    moves_heur, details = synthesize_mapping_details(str(src_xsd_path), 'Root', str(dst_xsd_path), 'Root', 'In/Root', 'Out/Root', enable_heuristics=True, heuristic_threshold=0.65)
    assert len(moves_heur) == 3
    # Coverage improved
    assert details['coverage']['mapped'] == 3
    assert details['coverage']['ratio'] == 1.0
    assert details['unmapped_dst'] == []
    # Confidence scores present; two heuristic entries with ratio < 1
    kinds = [m['kind'] for m in details['mapped']]
    assert 'heuristic' in kinds and 'identical' in kinds