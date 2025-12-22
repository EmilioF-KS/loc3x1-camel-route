package com.example.mock.api;

import com.example.mock.provider.MockLoader;
import com.example.mock.validation.XmlSchemaValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/crp10x1/v1")
@Tag(name = "CRP10X1", description = "Country retrieval mock service")
public class Crp10x1Controller {
    private final XmlSchemaValidator validator;
    private final MockLoader loader;

    public Crp10x1Controller(XmlSchemaValidator validator, MockLoader loader) {
        this.validator = validator;
        this.loader = loader;
    }

    @Operation(summary = "Get country")
    @PostMapping(path = "/get-country", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String getCountry(@RequestBody String body) throws Exception {
        validator.validateBody(body, "contracts/selected/CountryRetrievalCRP10X1/GetCountryRequest.xsd");
        String reply = loader.loadClasspath("mocks/crp10x1/GetCountryReply.xml");
        validator.validateBody(reply, "contracts/selected/CountryRetrievalCRP10X1/GetCountryReply.xsd");
        return reply;
    }
}
