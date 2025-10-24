package com.locationretrievalloc3x1process.webservice.model;

import lombok.Data;

@Data
public class Location {
    private String fireDistrictCode;

	public String getFireDistrictCode() {
		return fireDistrictCode;
	}

	public void setFireDistrictCode(String fireDistrictCode) {
		this.fireDistrictCode = fireDistrictCode;
	}

	@Override
	public String toString() {
		return "Location [fireDistrictCode=" + fireDistrictCode + "]";
	}
    
    
}
