import os
from lxml import etree
from agent.src.map_synthesis import synthesize_moves
from agent.src.map_move_to_xslt import generate_xslt_from_moves
from agent.src.validate_transform import wrapper_xsd_for_loc3x1b_request, validate_with_wrapper_schema


BASE_DIR = os.path.dirname(os.path.dirname(__file__))
PROJECT_ROOT = os.path.dirname(BASE_DIR)
LOC3X1B_DIR = os.path.join(PROJECT_ROOT, 'CHUBB', 'Dependencies', 'LocationRetrievalLOC3X1B')


def test_synthesize_moves_and_validate_request(tmp_path):
    src_xsd = os.path.join(LOC3X1B_DIR, 'GetLocationListRequest.xsd')
    dst_xsd = os.path.join(LOC3X1B_DIR, 'GetLocationListRequest.xsd')
    moves = synthesize_moves(
        src_xsd, 'GetLocationListRequest', dst_xsd, 'GetLocationListRequest',
        'GetLocationList/GetLocationListRequest', 'GetLocationList3X1B/GetLocationListRequest'
    )
    assert len(moves) >= 1

    xslt_text = generate_xslt_from_moves(moves)
    xslt_doc = etree.XSLT(etree.XML(xslt_text.encode('utf-8')))
    xml_in = etree.XML(
        b"""
        <GetLocationList>
          <GetLocationListRequest>
            <AddressLine1>237 Duffield Street</AddressLine1>
          </GetLocationListRequest>
        </GetLocationList>
        """
    )
    xml_out = xslt_doc(etree.ElementTree(xml_in))

    # Qualify only root for wrapper targetNamespace
    out_root = xml_out.getroot()
    if '}' not in out_root.tag:
        ns = 'http://ei/location/location_retrieval_loc3x1b'
        out_root.tag = f'{{{ns}}}{out_root.tag}'

    wrapper_xsd = wrapper_xsd_for_loc3x1b_request()
    validate_with_wrapper_schema(xml_out, wrapper_xsd, base_dir=LOC3X1B_DIR)