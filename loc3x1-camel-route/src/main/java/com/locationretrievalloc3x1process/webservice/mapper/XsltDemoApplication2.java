package com.locationretrievalloc3x1process.webservice.mapper;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@SpringBootApplication
public class XsltDemoApplication2 implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(XsltDemoApplication2.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //String xmlInput = "<greeting><name>Emilio</name></greeting>";
    	//String xmlInput = "<greeting><name>Emilio</name></greeting>";
    	String xmlInput2 = "C:\\CHUBB\\IA\\TO_XSLT\\sample_2.xml";
    	System.out.println(xmlInput2);
    	Path filePath = Paths.get(xmlInput2); // Replace with your file path

    	String xmlInput = null;
    	
        try {
        	xmlInput = Files.readString(filePath);
            System.out.println("xml: " + xmlInput);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        String xslStylesheetPath = "C:\\CHUBB\\IA\\TO_XSLT\\xslSample_2.xsl";
    	System.out.println(xslStylesheetPath);
    	Path xlsFilePath = Paths.get(xslStylesheetPath); // Replace with your file path

    	String xslStylesheet = null;
    	
        try {
        	xslStylesheet = Files.readString(xlsFilePath);
            System.out.println("xsl: " +  xslStylesheet);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    	
    	/*String xslStylesheet = """
            <?xml version="1.0" encoding="UTF-8"?>
            <xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                <xsl:template match="/greeting">
                    <hello>
                        <xsl:text>Hello, </xsl:text>
                        <xsl:value-of select="name"/>
                        <xsl:text>!</xsl:text>
                    </hello>
                </xsl:template>
            </xsl:stylesheet>
            """;*/

        String outputWriterRes = transformXml(xmlInput, xslStylesheet);
        
        //System.out.println("Transformed XML Result:");
        //System.out.println(outputWriterRes);
    }
    
    public static String transformXml(String xmlInput, String xslStylesheet) throws Exception {
  
    	//System.out.println("xmlInput ::::::: " + xmlInput);
    	
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new StringReader(xslStylesheet));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new StringReader(xmlInput));
        StringWriter outputWriter = new StringWriter();
        transformer.setParameter("fireDistrictCodeValue", "");
        transformer.setParameter("statusCode", "W");
        transformer.setParameter("errorDescription", "Missing Address Line 1 for FireDistrictCode");
        transformer.setParameter("errorSourceIdentifier", "");
        transformer.setParameter("errorCode", "W");
        transformer.setParameter("errorSeverityLevel", "");
        transformer.transform(text, new StreamResult(outputWriter));

        //System.out.println("Transformed XML:");
        //System.out.println(outputWriter.toString());
        
        return outputWriter.toString();
    }
}
