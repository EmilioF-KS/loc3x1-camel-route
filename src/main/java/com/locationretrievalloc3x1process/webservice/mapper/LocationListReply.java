package com.locationretrievalloc3x1process.webservice.mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

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
        String xmlInput2 = Paths.get(System.getProperty("user.dir"), "CHUBB", "sample_input_with_submaps.xml").toString();
    	Path filePath = Paths.get(xmlInput2);

    	String xmlInput = null;
    	
        try {
        	xmlInput = Files.readString(filePath);
            //System.out.println("xml: " + xmlInput);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        String xslStylesheetPath = Paths.get(System.getProperty("user.dir"), "CHUBB", "main_mapper_with_submaps.xsl").toString();
    	Path xlsFilePath = Paths.get(xslStylesheetPath);

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

        // Persist transformed XML to CHUBB/output for verification
        try {
        	Path outDir = Paths.get(System.getProperty("user.dir"), "CHUBB", "output");
        	Files.createDirectories(outDir);
        	Path outFile = outDir.resolve("loc3x1_result.xml");
        	Files.writeString(outFile, outputWriterRes, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
        	System.err.println("Error writing transformed XML file: " + ioe.getMessage());
        }
    }
    
    public static String getLoc3x1xmlFromRand() throws Exception {
        String xmlInput2 = Paths.get(System.getProperty("user.dir"), "CHUBB", "sample_input_with_submaps.xml").toString();
    	Path filePath = Paths.get(xmlInput2);

    	String xmlInput = null;
    	
        try {
        	xmlInput = Files.readString(filePath);
            //System.out.println("xml: " + xmlInput);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        
        String xslStylesheetPath = Paths.get(System.getProperty("user.dir"), "CHUBB", "main_mapper_with_submaps.xsl").toString();
    	Path xlsFilePath = Paths.get(xslStylesheetPath);

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
        
        // Persist transformed XML to CHUBB/output for verification
        try {
        	Path outDir = Paths.get(System.getProperty("user.dir"), "CHUBB", "output");
        	Files.createDirectories(outDir);
        	Path outFile = outDir.resolve("loc3x1_result.xml");
        	Files.writeString(outFile, outputWriterRes, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
        	System.err.println("Error writing transformed XML file: " + ioe.getMessage());
        }
        return outputWriterRes;
    }
    
    public static String getLoc3x1xmlFromRand(String xmlInput) throws Exception {
    	String xslStylesheetPath = Paths.get(System.getProperty("user.dir"), "CHUBB", "main_mapper_with_submaps.xsl").toString();
    	Path xlsFilePath = Paths.get(xslStylesheetPath);

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
        
        // Persist transformed XML to CHUBB/output for verification
        try {
        	Path outDir = Paths.get(System.getProperty("user.dir"), "CHUBB", "output");
        	Files.createDirectories(outDir);
        	Path outFile = outDir.resolve("loc3x1_result.xml");
        	Files.writeString(outFile, outputWriterRes, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
        	System.err.println("Error writing transformed XML file: " + ioe.getMessage());
        }
        return outputWriterRes;
    }
}
