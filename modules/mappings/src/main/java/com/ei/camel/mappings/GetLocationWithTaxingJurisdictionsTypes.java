package com.ei.camel.mappings;

// Minimal typed models to enable deterministic MapStruct mapping for LOC-010
public class GetLocationWithTaxingJurisdictionsTypes {
    public static class GetLocationWithTaxingJurisdictionsRequestMsg {
        public String addressLine1;
        public String addressLine2;
        public String cityName;
        public String stateOrProvinceCode;
        public String postalStateAbbreviation;
        public String postalCode;
        public String countryCode;
        public String countryAbbreviation;
        public String locationPlaceCode;
        public String asOfDate;
    }
    public static class GetLocationWithTaxingJurisdictions3X1BRequestMsg {
        public String addressLine1;
        public String addressLine2;
        public String cityName;
        public String stateOrProvinceCode;
        public String postalStateAbbreviation;
        public String postalCode;
        public String countryCode;
        public String countryAbbreviation;
        public String locationPlaceCode;
        public String asOfDate;
    }
}