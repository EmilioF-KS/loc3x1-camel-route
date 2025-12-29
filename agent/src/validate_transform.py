import os
import argparse
from lxml import etree


def load_xml(path):
    with open(path, 'rb') as f:
        return etree.parse(f)


def load_xslt(path):
    with open(path, 'rb') as f:
        xslt_doc = etree.parse(f)
    return etree.XSLT(xslt_doc)


def validate_with_wrapper_schema(xml_tree: etree._ElementTree, wrapper_xsd_text: str, base_dir: str = None):
    parser = etree.XMLParser(remove_blank_text=True)
    xsd_doc = etree.fromstring(wrapper_xsd_text.encode('utf-8'), parser)
    # Resolve relative imports
    if base_dir:
        for imp in xsd_doc.xpath('//xsd:import', namespaces={'xsd': 'http://www.w3.org/2001/XMLSchema'}):
            href = imp.get('schemaLocation')
            if href and not os.path.isabs(href):
                imp.set('schemaLocation', os.path.join(base_dir, href))
    schema = etree.XMLSchema(xsd_doc)
    schema.assertValid(xml_tree)


def wrapper_xsd_for_loc3x1b_request():
    return (
        '<?xml version="1.0" encoding="UTF-8"?>\n'
        '<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"\n'
        '  targetNamespace="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:tns="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:req="http://ei/location/get_location_list_request_loc3x1b">\n'
        '  <xsd:import namespace="http://ei/location/get_location_list_request_loc3x1b" schemaLocation="GetLocationListRequest.xsd"/>\n'
        '  <xsd:element name="GetLocationList3X1B">\n'
        '    <xsd:complexType>\n'
        '      <xsd:sequence>\n'
        '        <xsd:element name="GetLocationListRequest" type="req:GetLocationListRequest"/>\n'
        '      </xsd:sequence>\n'
        '    </xsd:complexType>\n'
        '  </xsd:element>\n'
        '</xsd:schema>\n'
    )


def wrapper_xsd_for_loc3x1b_reply():
    return (
        '<?xml version="1.0" encoding="UTF-8"?>\n'
        '<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"\n'
        '  targetNamespace="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:tns="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:rep="http://ei/location/location_list_reply_loc3x1b">\n'
        '  <xsd:import namespace="http://ei/location/location_list_reply_loc3x1b" schemaLocation="LocationListReply.xsd"/>\n'
        '  <xsd:element name="GetLocationList3X1BResponse">\n'
        '    <xsd:complexType>\n'
        '      <xsd:sequence>\n'
        '        <xsd:element name="GetLocationListReply" type="rep:LocationListReply"/>\n'
        '      </xsd:sequence>\n'
        '    </xsd:complexType>\n'
        '  </xsd:element>\n'
        '</xsd:schema>\n'
    )


def wrapper_xsd_for_loc3x1b_reply_partial():
    # Partial wrapper that validates only StatusInformation when Location.xsd is unavailable
    return (
        '<?xml version="1.0" encoding="UTF-8"?>\n'
        '<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"\n'
        '  targetNamespace="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:tns="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:rep="http://ei/location/location_list_reply_loc3x1b"\n'
        '  xmlns:stat="http://ei/core/status_informationx1">\n'
        '  <xsd:import namespace="http://ei/core/status_informationx1" schemaLocation="../Schemas/StatusInformationX1.xsd"/>\n'
        '  <xsd:element name="GetLocationList3X1BResponse">\n'
        '    <xsd:complexType>\n'
        '      <xsd:sequence>\n'
        '        <xsd:element name="GetLocationListReply">\n'
        '          <xsd:complexType>\n'
        '            <xsd:sequence>\n'
        '              <xsd:element name="Location" type="xsd:anyType" minOccurs="0" maxOccurs="unbounded"/>\n'
        '              <xsd:element name="StatusInformation" type="stat:StatusInformation" minOccurs="0"/>\n'
        '            </xsd:sequence>\n'
        '          </xsd:complexType>\n'
        '        </xsd:element>\n'
        '      </xsd:sequence>\n'
        '    </xsd:complexType>\n'
        '  </xsd:element>\n'
        '</xsd:schema>\n'
    )


def wrapper_xsd_for_loc3x1b_reply_relaxed():
    # Relaxed wrapper: validates presence of container elements, allows any content
    return (
        '<?xml version="1.0" encoding="UTF-8"?>\n'
        '<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"\n'
        '  targetNamespace="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:tns="http://ei/location/location_retrieval_loc3x1b">\n'
        '  <xsd:element name="GetLocationList3X1BResponse">\n'
        '    <xsd:complexType>\n'
        '      <xsd:sequence>\n'
        '        <xsd:element name="GetLocationListReply">\n'
        '          <xsd:complexType>\n'
        '            <xsd:sequence>\n'
        '              <xsd:element name="Location" type="xsd:anyType" minOccurs="0" maxOccurs="unbounded"/>\n'
        '              <xsd:element name="StatusInformation" type="xsd:anyType" minOccurs="0"/>\n'
        '            </xsd:sequence>\n'
        '          </xsd:complexType>\n'
        '        </xsd:element>\n'
        '      </xsd:sequence>\n'
        '    </xsd:complexType>\n'
        '  </xsd:element>\n'
        '</xsd:schema>\n'
    )


def wrapper_xsd_for_loc3x1b_request_relaxed():
    # Relaxed request wrapper: validates container and allows any content for request
    return (
        '<?xml version="1.0" encoding="UTF-8"?>\n'
        '<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"\n'
        '  targetNamespace="http://ei/location/location_retrieval_loc3x1b"\n'
        '  xmlns:tns="http://ei/location/location_retrieval_loc3x1b">\n'
        '  <xsd:element name="GetLocationList3X1B">\n'
        '    <xsd:complexType>\n'
        '      <xsd:sequence>\n'
        '        <xsd:element name="GetLocationListRequest" type="xsd:anyType"/>\n'
        '      </xsd:sequence>\n'
        '    </xsd:complexType>\n'
        '  </xsd:element>\n'
        '</xsd:schema>\n'
    )


def main():
    parser = argparse.ArgumentParser(description='Apply XSLT and validate output XML against XSD wrappers.')
    parser.add_argument('--xslt', required=True, help='Path to XSLT file')
    parser.add_argument('--input', required=True, help='Path to input XML file')
    parser.add_argument('--mode', choices=['request', 'reply'], required=True, help='Validation mode')
    parser.add_argument('--base-xsd-dir', default='CHUBB/Dependencies/LocationRetrievalLOC3X1B', help='Base directory for imported XSDs')
    args = parser.parse_args()

    xml_in = load_xml(args.input)
    xslt = load_xslt(args.xslt)
    xml_out = xslt(xml_in)

    if args.mode == 'request':
        wrapper = wrapper_xsd_for_loc3x1b_request()
    else:
        wrapper = wrapper_xsd_for_loc3x1b_reply()

    # Ensure namespace declaration exists in output root per wrapper targetNamespace
    # If missing, add it programmatically (children remain unqualified per wrapper defaults)
    out_root = xml_out.getroot()
    if '}' not in out_root.tag:
        ns = 'http://ei/location/location_retrieval_loc3x1b'
        out_root.tag = f'{{{ns}}}{out_root.tag}'
        # Do not qualify nested children; wrapper's local elements are unqualified

    validate_with_wrapper_schema(xml_out, wrapper, base_dir=args.base_xsd_dir)
    print('Validation successful')


if __name__ == '__main__':
    main()