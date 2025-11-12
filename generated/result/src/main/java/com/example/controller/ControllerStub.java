
package com.example.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/loc/getLocationList")
public class ControllerStub {

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String get() {
        return "<ok/>";
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String post() {
        return "<ok/>";
    }
}
