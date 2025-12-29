import time
import xml.etree.ElementTree as ET
from typing import Dict, Optional
def parse_xml(xml: str) -> ET.Element:
    return ET.fromstring(xml)
def extract_soap_body(root: ET.Element) -> ET.Element:
    ns = {'soap': 'http://schemas.xmlsoap.org/soap/envelope/'}
    if root.tag.endswith('Envelope'):
        body = root.find('soap:Body', ns)
        return list(body)[0] if body is not None and len(list(body)) > 0 else root
    return root
def validate_xml_with_xsd(xml: str, xsd_path: Optional[str]) -> Optional[str]:
    if not xsd_path:
        return None
    try:
        import xmlschema
        schema = xmlschema.XMLSchema(xsd_path)
        schema.validate(xml)
        return None
    except Exception as e:
        return str(e)
def build_fault(message: str) -> str:
    return ('<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">'            '<soap:Body><soap:Fault><faultcode>Client</faultcode><faultstring>' + message + '</faultstring>'            '</soap:Fault></soap:Body></soap:Envelope>')
def delay_ms(ms: int) -> None:
    if ms and ms > 0:
        time.sleep(ms / 1000.0)
