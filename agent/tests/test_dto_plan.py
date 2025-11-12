from pathlib import Path
import json

from agent.src.inventory import discover_artifacts, write_manifest
from agent.src.dto_plan import build_dto_plan


def test_dto_plan_synthetic_xsd(tmp_path):
    # Create synthetic XSD with complexType and element
    xsd = tmp_path / "Schemas" / "TestTypes.xsd"
    xsd.parent.mkdir(parents=True)
    xsd.write_text(
        """
        <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" targetNamespace="http://example/types" xmlns:tns="http://example/types">
          <xsd:complexType name="BaseType">
            <xsd:sequence>
              <xsd:element name="BaseField" type="xsd:string"/>
            </xsd:sequence>
            <xsd:attribute name="baseAttr" type="xsd:string" use="optional"/>
          </xsd:complexType>
          <xsd:complexType name="TestComplex">
            <xsd:complexContent>
              <xsd:extension base="tns:BaseType">
                <xsd:sequence>
                  <xsd:element name="Field" type="xsd:string" minOccurs="0"/>
                </xsd:sequence>
                <xsd:attribute name="extraAttr" type="xsd:int" use="optional"/>
              </xsd:extension>
            </xsd:complexContent>
          </xsd:complexType>
          <xsd:complexType name="ChoiceType">
            <xsd:choice>
              <xsd:element name="A" type="xsd:string"/>
              <xsd:element name="B" type="xsd:string"/>
            </xsd:choice>
          </xsd:complexType>
          <xsd:simpleType name="ColorType">
            <xsd:restriction base="xsd:string">
              <xsd:enumeration value="RED"/>
              <xsd:enumeration value="GREEN"/>
              <xsd:enumeration value="BLUE"/>
            </xsd:restriction>
          </xsd:simpleType>
          <xsd:element name="TestElement" type="tns:TestComplex"/>
          <xsd:element name="Color" type="tns:ColorType"/>
          <xsd:element name="InlineEnum">
            <xsd:simpleType>
              <xsd:restriction base="xsd:string">
                <xsd:enumeration value="X"/>
                <xsd:enumeration value="Y"/>
              </xsd:restriction>
            </xsd:simpleType>
          </xsd:element>
        </xsd:schema>
        """
    )

    artifacts = discover_artifacts(tmp_path)
    manifest_path = tmp_path / "manifest.json"
    write_manifest(manifest_path, artifacts, tmp_path)
    manifest = json.loads(manifest_path.read_text())

    plan = build_dto_plan(manifest)
    # Package mapping exists
    assert plan["packages"]["http://example/types"].startswith("example.types")
    # Class with extension, fields and attributes extracted
    tc = next(c for c in plan["classes"] if c["name"] == "TestComplex")
    assert tc["base_type"] == "tns:BaseType"
    assert any(f["name"] == "Field" and f["type"] == "xsd:string" for f in tc["fields"])
    assert any(a["name"] == "extraAttr" and a["type"] == "xsd:int" for a in tc["attributes"])
    # Element referencing the complex type
    te = next(e for e in plan["elements"] if e["name"] == "TestElement")
    assert te["type"] == "tns:TestComplex"
    # Inline simpleType enumerations captured
    inline_enum = next(e for e in plan["elements"] if e["name"] == "InlineEnum")
    assert set(inline_enum["simpleType"]["enumerations"]) == {"X", "Y"}