from pathlib import Path
import json

from agent.src.inventory import discover_artifacts, write_manifest
from agent.src.contracts import build_contracts


def test_contracts_extraction_synthetic(tmp_path):
    # Create synthetic XSD
    xsd = tmp_path / "Schemas" / "TestTypes.xsd"
    xsd.parent.mkdir(parents=True)
    xsd.write_text(
        """
        <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" targetNamespace="http://example/types" xmlns:tns="http://example/types">
          <xsd:complexType name="TestComplex">
            <xsd:sequence>
              <xsd:element name="Field" type="xsd:string"/>
            </xsd:sequence>
          </xsd:complexType>
          <xsd:element name="TestElement" type="tns:TestComplex"/>
        </xsd:schema>
        """
    )

    # Create synthetic WSDL referencing the namespace
    wsdl = tmp_path / "Service" / "Test.wsdl"
    wsdl.parent.mkdir(parents=True)
    wsdl.write_text(
        """
        <definitions xmlns="http://schemas.xmlsoap.org/wsdl/" targetNamespace="http://example/types" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/" xmlns:tns="http://example/types">
          <portType name="TestPortType">
            <operation name="DoThing">
              <input message="tns:DoThingRequest"/>
              <output message="tns:DoThingResponse"/>
            </operation>
          </portType>
          <message name="DoThingRequest">
            <part name="parameters" element="tns:TestElement"/>
          </message>
          <message name="DoThingResponse">
            <part name="parameters" element="tns:TestElement"/>
          </message>
        </definitions>
        """
    )

    artifacts = discover_artifacts(tmp_path)
    manifest_path = tmp_path / "manifest.json"
    write_manifest(manifest_path, artifacts, tmp_path)
    manifest = json.loads(manifest_path.read_text())

    contracts = build_contracts(manifest)
    # Validate WSDL contracts
    assert contracts["summary"]["wsdl_count"] == 1
    assert contracts["summary"]["xsd_count"] == 1
    # One operation captured
    assert contracts["summary"]["operations_count"] == 1
    # Types linked by namespace
    wsdl_contract = contracts["contracts"][0]
    assert wsdl_contract["targetNamespace"] == "http://example/types"
    assert any(ct["name"] == "TestComplex" for ct in wsdl_contract["types"]["complexTypes"])
    assert any(el["name"] == "TestElement" for el in wsdl_contract["types"]["elements"])