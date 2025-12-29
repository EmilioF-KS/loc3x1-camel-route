
package com.summary.controller.provider;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderStubController {
    @PostMapping(path = "/provider/locations", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public String invoke() {
        return "<LocationListReply><locations/></LocationListReply>";
    }
}
