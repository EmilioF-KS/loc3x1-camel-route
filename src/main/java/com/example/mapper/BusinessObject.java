package com.example.mapper;

public class BusinessObject {
    private String addressLine1;
    private String cityName;
    private String stateOrProvinceCode;
    private String countryCode;
    private String postalCode;
    private String locationPlaceCode;

    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getStateOrProvinceCode() { return stateOrProvinceCode; }
    public void setStateOrProvinceCode(String stateOrProvinceCode) { this.stateOrProvinceCode = stateOrProvinceCode; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getLocationPlaceCode() { return locationPlaceCode; }
    public void setLocationPlaceCode(String locationPlaceCode) { this.locationPlaceCode = locationPlaceCode; }
}
