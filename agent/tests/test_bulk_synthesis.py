import os
from lxml import etree
from agent.src.bulk_synthesis import bulk_generate_loc3x1b
from agent.src.validate_transform import (
    wrapper_xsd_for_loc3x1b_request,
    wrapper_xsd_for_loc3x1b_reply,
    validate_with_wrapper_schema,
)


BASE_DIR = os.path.dirname(os.path.dirname(__file__))
PROJECT_ROOT = os.path.dirname(BASE_DIR)
LOC3X1B_DIR = os.path.join(PROJECT_ROOT, 'CHUBB', 'Dependencies', 'LocationRetrievalLOC3X1B')


def test_bulk_synthesis_request_and_reply(tmp_path):
    out_dir = tmp_path
    generated = bulk_generate_loc3x1b(LOC3X1B_DIR, str(out_dir))
    assert any('GetLocationListRequest_from_GetLocationListRequest.xsl' in p for p in generated)
    assert any('LocationListReply_from_LocationListReply.xsl' in p for p in generated)

    # Request validation
    req_xslt_path = [p for p in generated if p.endswith('GetLocationListRequest_from_GetLocationListRequest.xsl')][0]
    xslt_req = etree.XSLT(etree.parse(req_xslt_path))
    xml_in_req = etree.XML(open(os.path.join(PROJECT_ROOT, 'examples', 'get_location_list_request_min.xml'), 'rb').read())
    xml_out_req = xslt_req(etree.ElementTree(xml_in_req))
    # Qualify only root for wrapper targetNamespace
    out_root = xml_out_req.getroot()
    if '}' not in out_root.tag:
        ns = 'http://ei/location/location_retrieval_loc3x1b'
        out_root.tag = f'{{{ns}}}{out_root.tag}'
    validate_with_wrapper_schema(xml_out_req, wrapper_xsd_for_loc3x1b_request(), base_dir=LOC3X1B_DIR)

    # Reply validation (minimal synthetic input)
    rep_xslt_path = [p for p in generated if p.endswith('LocationListReply_from_LocationListReply.xsl')][0]
    xslt_rep = etree.XSLT(etree.parse(rep_xslt_path))
    xml_in_rep = etree.XML(
        b"""
        <GetLocationList>
          <LocationListReply>
            <StatusInformation/>
          </LocationListReply>
        </GetLocationList>
        """
    )
    xml_out_rep = xslt_rep(etree.ElementTree(xml_in_rep))
    out_root_rep = xml_out_rep.getroot()
    if '}' not in out_root_rep.tag:
        ns = 'http://ei/location/location_retrieval_loc3x1b'
        out_root_rep.tag = f'{{{ns}}}{out_root_rep.tag}'
    # Validate reply using full wrapper (Location.xsd now available)
    validate_with_wrapper_schema(xml_out_rep, wrapper_xsd_for_loc3x1b_reply(), base_dir=LOC3X1B_DIR)