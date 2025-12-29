from fastapi import Response
import logging
from ..utils.xml import parse_xml, extract_soap_body, validate_xml_with_xsd, build_fault, delay_ms
class LocationRetrievalLOC3X2BGetLocation3X2BController:
    async def handle(self, xml: str, delay: int = 0):
        logger = logging.getLogger('mock-services')
        logger.info('request %s %s', 'LocationRetrievalLOC3X2B', 'GetLocation3X2B')
        delay_ms(delay)
        err = validate_xml_with_xsd(xml, '') if '' else None
        if err:
            body = build_fault('invalid request')
            return Response(content=body, media_type='application/xml', status_code=400)
        root = parse_xml(xml)
        body_node = extract_soap_body(root)
        rep_root = 'GetLocation3X2BResponse'
        if '':
            try:
                import re
                t = open('generated/mock-services-py-result60\\resources\\', 'r', encoding='utf-8').read()
                m = re.search(r"<\s*xs:element\s+name=\"([^\"]+)\"", t)
                if m:
                    rep_root = m.group(1)
            except Exception:
                pass
        body = f"<{rep_root}><status>OK</status></{rep_root}>"
        logger.info('response %s %s', 'LocationRetrievalLOC3X2B', 'GetLocation3X2B')
        return Response(content=body, media_type='application/xml', status_code=200)
