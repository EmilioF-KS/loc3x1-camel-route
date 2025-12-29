from agent.src.map_move_to_xslt import parse_moves_from_ibm_map, generate_xslt_from_moves, parse_submaps_from_ibm_map
from lxml import etree


def test_parse_moves_from_ibm_map_simple():
    xml = """
    <map:businessObjectMap xmlns:map="http://www.ibm.com/xmlns/prod/websphere/wbiserver/map/6.0.0">
      <map:propertyMap executionOrder="1">
        <map:move>
          <map:input businessObjectVariableRef="In" property="RootIn/A/B"/>
          <map:output businessObjectVariableRef="Out" property="RootOut/C/D"/>
        </map:move>
      </map:propertyMap>
      <map:propertyMap executionOrder="2">
        <map:move>
          <map:input businessObjectVariableRef="In" property="RootIn/X"/>
          <map:output businessObjectVariableRef="Out" property="RootOut/Y/Z"/>
        </map:move>
      </map:propertyMap>
    </map:businessObjectMap>
    """
    moves = parse_moves_from_ibm_map(xml)
    assert len(moves) == 2
    assert moves[0]['input_property'] == 'RootIn/A/B'
    assert moves[0]['output_property'] == 'RootOut/C/D'
    assert moves[1]['input_property'] == 'RootIn/X'
    assert moves[1]['output_property'] == 'RootOut/Y/Z'


def test_generate_xslt_from_moves_contains_expected_structure():
    moves = [
        {
            'input_property': 'RootIn/A/B',
            'output_property': 'RootOut/C/D',
            'input_var': 'In',
            'output_var': 'Out',
        },
        {
            'input_property': 'RootIn/X',
            'output_property': 'RootOut/Y/Z',
            'input_var': 'In',
            'output_var': 'Out',
        },
    ]
    xslt = generate_xslt_from_moves(moves)
    # Root element should be RootOut
    assert '<xsl:element name="RootOut">' in xslt
    # First move nested elements
    assert '<xsl:element name="C">' in xslt
    assert '<xsl:element name="D">' in xslt
    assert 'select="RootIn/A/B"' in xslt
    # Second move nested elements
    assert '<xsl:element name="Y">' in xslt
    assert '<xsl:element name="Z">' in xslt
    assert 'select="RootIn/X"' in xslt


def test_parse_submaps_and_emit_copy_of():
    xml = """
    <map:businessObjectMap xmlns:map="http://www.ibm.com/xmlns/prod/websphere/wbiserver/map/6.0.0">
      <map:propertyMap executionOrder="1">
        <map:submap submapName="Something:Sub">
          <map:input businessObjectVariableRef="In" property="RootIn/Location" variableName="Location"/>
          <map:output businessObjectVariableRef="Out" property="RootOut/Location" variableName="Location_1"/>
        </map:submap>
      </map:propertyMap>
    </map:businessObjectMap>
    """
    subs = parse_submaps_from_ibm_map(xml)
    assert len(subs) == 1
    xslt = generate_xslt_from_moves([], subs)
    assert '<xsl:copy-of select="RootIn/Location"' in xslt


def test_xslt_application_roundtrip_simple():
    # End-to-end: apply generated XSLT to input XML and ensure elements appear
    moves = [{
        'input_property': 'RootIn/A/B',
        'output_property': 'RootOut/C/D',
        'input_var': 'In',
        'output_var': 'Out',
    }]
    xslt_text = generate_xslt_from_moves(moves)
    xslt_doc = etree.XSLT(etree.XML(xslt_text.encode('utf-8')))
    xml_in = etree.XML(b'<RootIn><A><B>val</B></A></RootIn>')
    out = xslt_doc(etree.ElementTree(xml_in))
    out_str = etree.tostring(out, pretty_print=True).decode('utf-8')
    assert '<C>' in out_str and '<D>' in out_str and 'val' in out_str