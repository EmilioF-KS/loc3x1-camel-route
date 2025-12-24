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
@RequestMapping("/api/loc3x1b/v1")
@Tag(name = "LOC3X1B", description = "Location retrieval mock service")
public class Loc3x1bController {
    private final XmlSchemaValidator validator;
    private final MockLoader loader;

    public Loc3x1bController(XmlSchemaValidator validator, MockLoader loader) {
        this.validator = validator;
        this.loader = loader;
    }

    @Operation(summary = "Get location list")
    @PostMapping(path = "/get-location-list", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String getLocationList(@RequestBody String body) throws Exception {
        validator.validateBody(body, "contracts/selected/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd");
        String reply = loader.loadClasspath("mocks/loc3x1b/LocationListReply.xml");
        validator.validateBody(reply, "contracts/selected/LocationRetrievalLOC3X1B/LocationListReply.xsd");
        return reply;
    }
}
