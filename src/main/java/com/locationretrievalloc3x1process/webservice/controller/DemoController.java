package com.locationretrievalloc3x1process.webservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.locationretrievalloc3x1process.webservice.mapper.LocationListReply;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/xslt-output")
    public Map<String, String> xsltOutput() throws Exception {
        String xml = LocationListReply.getLoc3x1xmlFromRand();
        Path outFile = Paths.get(System.getProperty("user.dir"), "CHUBB", "output", "loc3x1_result.xml");
        boolean exists = Files.exists(outFile);

        Map<String, String> res = new HashMap<>();
        res.put("saved", Boolean.toString(exists));
        res.put("path", outFile.toString());
        res.put("bytes", Integer.toString(xml != null ? xml.length() : 0));
        return res;
    }
}