
package com.example.location.model;

import lombok.Data;

@Data
public class LocationRequest {
    private String addressLine1;
    private String addressLine2;
    private String cityName;
    private String stateOrProvinceCode;
    private String postalStateAbbreviation;
    private String postalCode;
    private String locationPlaceCode;
    private String countryCode;
    private String countryAbbreviation;
}
