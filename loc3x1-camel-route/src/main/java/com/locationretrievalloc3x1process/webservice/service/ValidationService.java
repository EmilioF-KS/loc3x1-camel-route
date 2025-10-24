package com.locationretrievalloc3x1process.webservice.service;

import org.springframework.stereotype.Service;

import com.locationretrievalloc3x1process.webservice.model.LocationRequest;

@Service
public class ValidationService {
    public boolean validate(LocationRequest req) {
    	System.out.println("1		ValidationService.validate : " + req);
        if (req == null || (isEmpty(req.getPostalCode()) &&
            isEmpty(req.getLocationPlaceCode()) &&
            isEmpty(req.getStateOrProvinceCode()) &&
            isEmpty(req.getPostalStateAbbreviation()))) {
            
        	return false;
        }
        System.out.println("Correct validation : true ");
        return true;
    }
    
    public boolean validateWithTaxingJurisdictions(LocationRequest req) {
    	System.out.println("1		ValidationService.validateWithTaxingJurisdictions : " + req);
        if (req != null && (!isEmpty(req.getPostalCode()) &&
            (!isEmpty(req.getStateOrProvinceCode()) || !isEmpty(req.getPostalStateAbbreviation())))) {
        
        	System.out.println("Correct validation : true ");
        	return true;
        }
        
        return false;
    }

    private boolean isEmpty(String val) {
        return val == null || val.trim().isEmpty();
    }
}
