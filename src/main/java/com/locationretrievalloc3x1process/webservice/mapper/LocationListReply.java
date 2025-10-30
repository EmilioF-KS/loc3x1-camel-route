package com.locationretrievalloc3x1process.webservice.mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class LocationListReply implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LocationListReply.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String xmlInput2 = "C:\\CHUBB\\IA\\TO_XSLT\\WithSubmappers\\Complex\\sample_input_with_submaps.xml";
    	//System.out.println(xmlInput2);
    	Path filePath = Paths.get(xmlInput2); // Replace with your file path

    	String xmlInput = null;
    	
        try {
        	xmlInput = Files.readString(filePath);
            //System.out.println("xml: " + xmlInput);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        String xslStylesheetPath = "C:\\CHUBB\\IA\\TO_XSLT\\WithSubmappers\\Complex\\main_mapper_with_submaps.xsl";
    	//System.out.println(xslStylesheetPath);
    	Path xlsFilePath = Paths.get(xslStylesheetPath); // Replace with your file path

    	String xslStylesheet = null;
    	
        try {
        	xslStylesheet = Files.readString(xlsFilePath);
            //System.out.println("xsl: " +  xslStylesheet);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    	
        String outputWriterRes = XsltDemoApplication2.transformXml(xmlInput, xslStylesheet);
        
        //System.out.println("Transformed XML Result:");
        //System.out.println(outputWriterRes);
    }
    
    public static String getLoc3x1xmlFromRand() throws Exception {
        String xmlInput2 = "C:\\CHUBB\\sample_input_with_submaps.xml";
    	//System.out.println(xmlInput2);
    	Path filePath = Paths.get(xmlInput2); // Replace with your file path

    	String xmlInput = null;
    	
        try {
        	xmlInput = Files.readString(filePath);
            //System.out.println("xml: " + xmlInput);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        String xslStylesheetPath = "C:\\CHUBB\\main_mapper_with_submaps.xsl";
    	//System.out.println(xslStylesheetPath);
    	Path xlsFilePath = Paths.get(xslStylesheetPath); // Replace with your file path

    	String xslStylesheet = null;
    	
        try {
        	xslStylesheet = Files.readString(xlsFilePath);
            //System.out.println("xsl: " +  xslStylesheet);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    	
        String outputWriterRes = XsltDemoApplication2.transformXml(xmlInput, xslStylesheet);
        
        //System.out.println("Transformed XML Result:");
        //System.out.println(outputWriterRes);
        
        return outputWriterRes;
    }
    
    public static String getLoc3x1xmlFromRand(String xmlInput) throws Exception {
    	String xslStylesheetPath = "C:\\CHUBB\\main_mapper_with_submaps.xsl";
    	Path xlsFilePath = Paths.get(xslStylesheetPath); // Replace with your file path

    	String xslStylesheet = null;
    	
        try {
        	xslStylesheet = Files.readString(xlsFilePath);
            //System.out.println("xsl: " +  xslStylesheet);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    	
        String outputWriterRes = XsltDemoApplication2.transformXml(xmlInput, xslStylesheet);
        
        System.out.println("::::::::::::::::::::::::::::::::::::: Transformed XML Result:");
        System.out.println(outputWriterRes);
        
        return outputWriterRes;
    }
}
