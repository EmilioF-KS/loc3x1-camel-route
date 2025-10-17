package com.locationretrievalloc3x1process.webservice.service.mfc;

import javax.xml.parsers.DocumentBuilderFactory;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

public class OutputTerminalTypeFinder {
	private static final String MFC_COMPLETE_PATH = "C:\\CHUBB\\LocationServices\\LocationRetrievalLOC3X1Mediation.xml";

	private static String getOutputTerminalType(Document doc) {
        NodeList outputTerminals = doc.getElementsByTagName("outputTerminal");

        for (int i = 0; i < outputTerminals.getLength(); i++) {
            Element outputTerminal = (Element) outputTerminals.item(i);
            NodeList wires = outputTerminal.getElementsByTagName("wire");

            for (int j = 0; j < wires.getLength(); j++) {
                Element wire = (Element) wires.item(j);
                if ("EndpointLookup".equals(wire.getAttribute("targetNode"))) {
                    return outputTerminal.getAttribute("type");
                }
            }
        }

        return null; // Not found
    }
    
	private static String getMapToRand(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("node");
        //System.out.println(":: nodeList: " + nodeList);

        for (int i = 0; i < nodeList.getLength(); i++) {
            Element nodeTerminal = (Element) nodeList.item(i);
            String name = nodeTerminal.getAttribute("name");
            //System.out.println(":: name: " + name);

            if ("MapToRand".equals(name)) {
            	NodeList nodeTerminals = nodeList;
            	//System.out.println(":: nodeTerminals :: " + nodeTerminals);
            	
            	for (int k = 0; k < nodeTerminals.getLength(); k++) {
            		Element outputTerminal = (Element) nodeTerminals.item(k);
                    NodeList properties = outputTerminal.getElementsByTagName("property");

                    for (int j = 0; j < properties.getLength(); j++) {
                        Element property = (Element) properties.item(j);
                        if ("mappingFile".equals(property.getAttribute("name"))) {
                        	//System.out.println("property value: " + property.getAttribute("value"));
                            return property.getAttribute("value");
                        }
                    }
                }
            }
        }

        return null; // Not found
    }
    
	private static List<String> getLocationsRequest(Document doc) {
        NodeList nodeList = doc.getElementsByTagName("map:move");
        List<String> requestAttributes = new ArrayList<String>();

        for (int i = 0; i < nodeList.getLength(); i++) {
       		Element outputTerminal = (Element) nodeList.item(i);
            NodeList properties = outputTerminal.getElementsByTagName("map:output");

            for (int j = 0; j < properties.getLength(); j++) {
                Element property = (Element) properties.item(j);
               	//System.out.println("property value: " + property.getAttribute("property"));
               	requestAttributes.add(property.getAttribute("property"));
            }           
        }

        return requestAttributes; // Not found
    }

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(MFC_COMPLETE_PATH); // Replace with your XML file path

        //System.out.println("doc: " + doc);
        
        String mapToRand = null;
        String type = getOutputTerminalType(doc);
        System.out.println("OutputTerminal type: " + type);
        
        if (type.contains("service.rand.chubb.com")) {
        	mapToRand = getMapToRand(doc);
        	//System.out.println("mapToRand value: " + mapToRand);
        }
        
        if (mapToRand != null) {
        	DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory1.newDocumentBuilder();
            
            //String[] mapFileArr = mapToRand.split(".");
            //for (String mapFile : mapFileArr) {
            	//System.out.println("mapFile " + mapFile);
            //}
            //System.out.println("mapFileArr: " + mapFileArr);
            //System.out.println("mapFileArr[0]: " + mapFileArr[0]);
            //String mapPath = "C:\\CHUBB\\LocationServices\\" + mapFileArr[0].replace("/", "\\") + ".xml";
            String mapPath = "C:\\CHUBB\\LocationServices\\" + mapToRand.substring(0, mapToRand.length() - 4).replace("/", "\\") + ".xml";
            
            //String mapPath = "C:\\CHUBB\\LocationServices\\LOC3X1\\LOC3X1MGetLocationListRequestMap.xml";
            //System.out.println("mapPath: " + mapPath);
            Document doc1 = builder1.parse(mapPath); // Replace with your XML file path
            getLocationsRequest(doc1);
        }
    }
    
    public static List<String> getMappingAttributes() throws Exception {
    	System.out.println("4		Getting Endpoint to Execute" );
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(MFC_COMPLETE_PATH); // Replace with your XML file path

        String mapToRand = null;
        String type = getOutputTerminalType(doc);
        //System.out.println("OutputTerminal type: " + type);
        
        if (type.contains("service.rand.chubb.com")) {
        	mapToRand = getMapToRand(doc);
        	//System.out.println("mapToRand value: " + mapToRand);
        }
        
        if (mapToRand != null) {
        	DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory1.newDocumentBuilder();
            String mapPath = "C:\\CHUBB\\LocationServices\\" + mapToRand.substring(0, mapToRand.length() - 4).replace("/", "\\") + ".xml";
            
            //System.out.println("mapPath: " + mapPath);
            
            //System.out.println("5		Getting Input Attributes for Endpoint" );
            Document doc1 = builder1.parse(mapPath); // Replace with your XML file path
            return getLocationsRequest(doc1);
        }
        
        return null;
    }
}
