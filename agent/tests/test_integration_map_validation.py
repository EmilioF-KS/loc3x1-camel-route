import os
from lxml import etree
from agent.src.map_move_to_xslt import convert_file
from agent.src.validate_transform import wrapper_xsd_for_loc3x1b_request, validate_with_wrapper_schema


BASE_DIR = os.path.dirname(os.path.dirname(__file__))
PROJECT_ROOT = os.path.dirname(BASE_DIR)
LOC3X1B_DIR = os.path.join(PROJECT_ROOT, 'CHUBB', 'Dependencies', 'LocationRetrievalLOC3X1B')


def test_request_map_transform_and_xsd_validation(tmp_path):
    # Generate XSLT from IBM map [request]
    input_map = os.path.join(PROJECT_ROOT, 'CHUBB', '00-Emilio-Logic', 'a7', 'LOC3X1GetLocationListRequestMap.xml')
    out_dir = tmp_path
    xslt_path = convert_file(input_map, str(out_dir))

    # Build synthetic source XML matching map input paths
    xml_in = etree.XML(
        b"""
        <GetLocationList>
          <GetLocationListRequest>
            <AddressLine1>237 Duffield Street</AddressLine1>
            <AddressLine2>Apt 2</AddressLine2>
            <CityName>BROOKLYN</CityName>
            <StateOrProvinceCode>31</StateOrProvinceCode>
            <PostalStateAbbreviation>NY</PostalStateAbbreviation>
            <PostalCode>11242</PostalCode>
            <LocationPlaceCode>10022</LocationPlaceCode>
            <CountryCode>8A</CountryCode>
            <CountryAbbreviation>USA</CountryAbbreviation>
          </GetLocationListRequest>
        </GetLocationList>
        """
    )

    # Apply XSLT
    xslt_doc = etree.XSLT(etree.parse(xslt_path))
    xml_out = xslt_doc(etree.ElementTree(xml_in))

    # Qualify only the root to match wrapper targetNamespace (children unqualified)
    out_root = xml_out.getroot()
    if '}' not in out_root.tag:
        ns = 'http://ei/location/location_retrieval_loc3x1b'
        out_root.tag = f'{{{ns}}}{out_root.tag}'

    wrapper_xsd = wrapper_xsd_for_loc3x1b_request()
    validate_with_wrapper_schema(xml_out, wrapper_xsd, base_dir=LOC3X1B_DIR)