package com.example.validation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.camel.Exchange;
import javax.xml.XMLConstants;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.w3c.dom.Node;
import java.net.URL;

@Component("Dependencies2Validator")
public class Dependencies2Validator {
    @Value("${dependencies2.baseUri}")
    private String baseUri;

    public void validateGetLocationRequest(Exchange exchange) throws Exception {
        Node node = exchange.getMessage().getBody(Node.class);
        validateWithWrapper(node,
                baseUri + "/LocationRetrievalLOC3X2B/GetLocationRequest.xsd",
                "http://ei/location/get_location_request_loc3x2b",
                "GetLocationRequest");
    }

    public void validateGetLocationReply(Exchange exchange) throws Exception {
        Node node = exchange.getMessage().getBody(Node.class);
        validateWithWrapper(node,
                baseUri + "/LocationRetrievalLOC3X2B/GetLocationReply.xsd",
                "http://ei/location/get_location_reply_loc3x2b",
                "GetLocationReply");
    }

    private void validate(Node node, String xsdUrl) throws Exception {
        var sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        sf.setResourceResolver(new BaseUriResourceResolver());
        URL url = new URL(xsdUrl);
        try (var in = url.openStream()) {
            var source = new StreamSource(in, url.toExternalForm());
            Validator validator = sf.newSchema(source).newValidator();
            validator.validate(new DOMSource(node));
        }
    }

    private void validateWithWrapper(Node node, String xsdUrl, String namespaceUri, String localElement) throws Exception {
        var sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        sf.setResourceResolver(new BaseUriResourceResolver());
        URL url = new URL(xsdUrl);
        try (var in = url.openStream()) {
            var main = new StreamSource(in, url.toExternalForm());
            String wrapper = "" +
                "<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"" + namespaceUri + "\" xmlns:tns=\"" + namespaceUri + "\">" +
                "<xsd:import namespace=\"" + namespaceUri + "\" schemaLocation=\"" + url.getPath().substring(url.getPath().lastIndexOf('/') + 1) + "\"/>" +
                "<xsd:element name=\"" + localElement + "\" type=\"tns:" + localElement + "\"/>" +
                "</xsd:schema>";
            var aux = new StreamSource(new java.io.StringReader(wrapper));
            Validator validator = sf.newSchema(new javax.xml.transform.Source[]{main, aux}).newValidator();
            validator.validate(new DOMSource(node));
        }
    }

    private class BaseUriResourceResolver implements LSResourceResolver {
        @Override
        public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
            try {
                URL resolved;
                if (systemId != null && systemId.contains("../Schemas/")) {
                    String name = systemId.substring(systemId.lastIndexOf('/') + 1);
                    if ("StandardizedAddressX2.xsd".equals(name) || "LocationX1.xsd".equals(name)) {
                        resolved = new URL(baseUri + "/LocationLibrary_Schemas/" + name);
                    } else if ("StatusInformationX1.xsd".equals(name)) {
                        resolved = new URL(baseUri + "/CoreLibrary_Schemas/" + name);
                    } else {
                        URL base = baseURI != null ? new URL(baseURI) : new URL(baseUri + "/");
                        resolved = new URL(base, systemId);
                    }
                } else {
                    URL base = baseURI != null ? new URL(baseURI) : new URL(baseUri + "/");
                    String name = systemId != null ? systemId.substring(systemId.lastIndexOf('/') + 1) : null;
                    if ("AddressX2.xsd".equals(name)) {
                        resolved = new URL(baseUri + "/CoreLibrary_Schemas/AddressX2.xsd");
                    } else {
                        resolved = new URL(base, systemId);
                    }
                }
                var in = resolved.openStream();
                return new SimpleLSInput(publicId, resolved.toExternalForm(), in);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private static class SimpleLSInput implements LSInput {
        private String publicId;
        private String systemId;
        private java.io.InputStream byteStream;

        SimpleLSInput(String publicId, String systemId, java.io.InputStream byteStream) {
            this.publicId = publicId;
            this.systemId = systemId;
            this.byteStream = byteStream;
        }

        @Override public java.io.Reader getCharacterStream() { return null; }
        @Override public void setCharacterStream(java.io.Reader characterStream) {}
        @Override public java.io.InputStream getByteStream() { return byteStream; }
        @Override public void setByteStream(java.io.InputStream byteStream) { this.byteStream = byteStream; }
        @Override public String getStringData() { return null; }
        @Override public void setStringData(String stringData) {}
        @Override public String getSystemId() { return systemId; }
        @Override public void setSystemId(String systemId) { this.systemId = systemId; }
        @Override public String getPublicId() { return publicId; }
        @Override public void setPublicId(String publicId) { this.publicId = publicId; }
        @Override public String getBaseURI() { return null; }
        @Override public void setBaseURI(String baseURI) {}
        @Override public String getEncoding() { return null; }
        @Override public void setEncoding(String encoding) {}
        @Override public boolean getCertifiedText() { return false; }
        @Override public void setCertifiedText(boolean certifiedText) {}
    }
}
