package com.locationretrievalloc3x1process.webservice.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateGeneratorService {

    public void generateMapperClass() throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassForTemplateLoading(this.getClass(), "/templates");
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate("businessObjectMapper.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("packageName", "com.locationretrievalloc3x1process.webservice.mapper");
        dataModel.put("className", "BusinessObjectMapper");
        dataModel.put("dtoClass", "GetLocationsRequestDTO");
        dataModel.put("entityClass", "BusinessObject");

        File outputFile = new File("C:\\KSquare projects\\Chubb-ibm\\_DEMO_3\\locationretrievalloc3x1process\\src\\main\\java\\com\\locationretrievalloc3x1process\\webservice\\Mapper\\BusinessObjectMapper.java");
        try (Writer out = new FileWriter(outputFile)) {
            template.process(dataModel, out);
        }

        System.out.println("Mapper class generated at: " + outputFile.getAbsolutePath());
    }
}
