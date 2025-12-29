from fastapi import Response
import logging
from ..utils.xml import parse_xml, extract_soap_body, validate_xml_with_xsd, build_fault, delay_ms
class CountryRetrievalCRP10X1GetCountryController:
    async def handle(self, xml: str, delay: int = 0):
        logger = logging.getLogger('mock-services')
        logger.info('request %s %s', 'CountryRetrievalCRP10X1', 'GetCountry')
        delay_ms(delay)
        err = validate_xml_with_xsd(xml, 'contracts\selected\GetCountryRequest.xsd') if 'contracts\selected\GetCountryRequest.xsd' else None
        if err:
            body = build_fault('invalid request')
            return Response(content=body, media_type='application/xml', status_code=400)
        root = parse_xml(xml)
        body_node = extract_soap_body(root)
        rep_root = 'GetCountryResponse'
        if 'contracts\selected\GetCountryListReply.xsd':
            try:
                import re
                t = open('generated/mock-services-py-result63\\resources\\contracts\\selected\\GetCountryListReply.xsd', 'r', encoding='utf-8').read()
                m = re.search(r"<\s*xs:element\s+name=\"([^\"]+)\"", t)
                if m:
                    rep_root = m.group(1)
            except Exception:
                pass
        body = f"<{rep_root}><status>OK</status></{rep_root}>"
        logger.info('response %s %s', 'CountryRetrievalCRP10X1', 'GetCountry')
        return Response(content=body, media_type='application/xml', status_code=200)
