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
@RequestMapping("/api/crp11x1/v1")
@Tag(name = "CRP11X1", description = "State/Province retrieval mock service")
public class Crp11x1Controller {
    private final XmlSchemaValidator validator;
    private final MockLoader loader;

    public Crp11x1Controller(XmlSchemaValidator validator, MockLoader loader) {
        this.validator = validator;
        this.loader = loader;
    }

    @Operation(summary = "Get states/provinces")
    @PostMapping(path = "/get-state-or-province", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String getStateOrProvince(@RequestBody String body) throws Exception {
        validator.validateBody(body, "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceRequest.xsd");
        String reply = loader.loadClasspath("mocks/crp11x1/GetStateOrProvinceListReply.xml");
        validator.validateBody(reply, "contracts/selected/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceListReply.xsd");
        return reply;
    }
}
