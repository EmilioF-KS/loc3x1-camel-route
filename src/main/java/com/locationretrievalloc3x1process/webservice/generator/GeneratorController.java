package com.locationretrievalloc3x1process.webservice.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import freemarker.template.TemplateException;

import java.io.IOException;

@RestController
@RequestMapping("/generate")
public class GeneratorController {

    @Autowired
    private TemplateGeneratorService generatorService;

    @GetMapping("/mapper")
    public ResponseEntity<String> generateMapper() {
        try {
            generatorService.generateMapperClass();
            return ResponseEntity.ok("Mapper class generated successfully.");
        } catch (IOException | TemplateException e) {
            return ResponseEntity.status(500).body("Error generating mapper: " + e.getMessage());
        }
    }
}
