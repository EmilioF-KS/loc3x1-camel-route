package com.locationretrievalloc3x1process.webservice.service;

import org.springframework.stereotype.Service;

import com.locationretrievalloc3x1process.webservice.dto.SimpleFault;

@Service("simpleFaultService")
public class SimpleFaultService {

    public SimpleFault createFault() {
    	System.out.println("-> SimpleFaultService.createFault ");
        
    	SimpleFault fault = new SimpleFault();
        fault.setFaultMessageText("Required service request data fields are missing.");
        fault.setErrorCode("EIRV0010E");
        fault.setSource("LocationRetrievalLOC3X1B");

    	System.out.println(": fault : " + fault);
    	
        return fault;
    }
}