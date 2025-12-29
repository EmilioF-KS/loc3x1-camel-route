package com.summary.validation;

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
  @Value("${dependencies2.baseUri:http://localhost:8091/contracts/selected}")
  private String baseUri;

  public void validateGetLocationRequest(Exchange exchange) throws Exception {
    Node node = exchange.getMessage().getBody(Node.class);
    validateWithWrapper(node,
      baseUri + "/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd",
      "http://ei/location/get_location_list_request_loc3x1b",
      "GetLocationListRequest");
  }

  public void validateGetLocationReply(Exchange exchange) throws Exception {
    Node node = exchange.getMessage().getBody(Node.class);
    validateWithWrapper(node,
      baseUri + "/LocationRetrievalLOC3X1B/LocationListReply.xsd",
      "http://ei/location/location_list_reply_loc3x1b",
      "LocationListReply");
  }

  private void validateWithWrapper(Node node, String xsdUrl, String ns, String local) throws Exception {
    var sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setResourceResolver(new BaseUriResourceResolver());
    URL url = new URL(xsdUrl);
    try (var in = url.openStream()) {
      var main = new StreamSource(in, url.toExternalForm());
      String wrapper = "<xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" targetNamespace=\"" + ns + "\" xmlns:tns=\"" + ns + "\">" +
        "<xsd:import namespace=\"" + ns + "\" schemaLocation=\"" + url.getPath().substring(url.getPath().lastIndexOf('/') + 1) + "\"/>" +
        "<xsd:element name=\"" + local + "\" type=\"tns:" + local + "\"/>" +
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
        URL base = baseURI != null ? new URL(baseURI) : new URL(baseUri + "/");
        URL resolved = new URL(base, systemId);
        var in = resolved.openStream();
        return new SimpleLSInput(publicId, resolved.toExternalForm(), in);
      } catch (Exception e) {
        return null;
      }
    }
  }

  private static class SimpleLSInput implements LSInput {
    private String publicId; private String systemId; private java.io.InputStream byteStream;
    SimpleLSInput(String publicId, String systemId, java.io.InputStream byteStream) { this.publicId = publicId; this.systemId = systemId; this.byteStream = byteStream; }
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
