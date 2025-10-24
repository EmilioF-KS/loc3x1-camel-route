package com.locationretrievalloc3x1process.webservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chubb.xsd.ei.core.error.Error;
import com.chubb.xsd.ei.location.locationinformationx2.LocationInformation;

@Service
public class FireDistrictService {
	private static final String MISSING_ADDRESS_LINE_1_FOR_FIREDISTRICTCODE = "Missing Address Line 1 for FireDistrictCode";
	private static final String FIREDISTRICTCODE_9999 = "9999";
	private static final String FIREDISTRICTCODE_WARNING_STATUS = "W"; 
			
    public void checkAndWarn(com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply reply) {
    	System.out.println("-> FireDistrictService.checkAndWarn ");
    	System.out.println(": reply : " + reply);
    	System.out.println(": reply.getLocation : " + reply.getLocation());
    	
    	LocationInformation locationInformation = null; 
    	List<Error> errors = null;
    	Error error = null;
    			
        for ( com.chubb.xsd.ei.location.locationx5.Location loc : reply.getLocation()) {
        	locationInformation = loc.getLocationInformation();
        	System.out.println("--> locationInformation : " + locationInformation);
        	
            if (locationInformation.getFireDistrictCode().trim().equalsIgnoreCase(FIREDISTRICTCODE_9999)) {
            	locationInformation.setFireDistrictCode("");
                System.out.println("--> " + MISSING_ADDRESS_LINE_1_FOR_FIREDISTRICTCODE);
                reply.getStatusInformation().setStatusCode(FIREDISTRICTCODE_WARNING_STATUS);
                
                error = new Error();
                error.setErrorCode(FIREDISTRICTCODE_WARNING_STATUS);
                error.setErrorDescription(MISSING_ADDRESS_LINE_1_FOR_FIREDISTRICTCODE);
				errors = reply.getStatusInformation().getError();
				
				if (errors == null) {
					errors = new ArrayList<>();					
				}
				
				errors.add(error);
				reply.getStatusInformation().setError(errors);
            }
        }
    }
}
