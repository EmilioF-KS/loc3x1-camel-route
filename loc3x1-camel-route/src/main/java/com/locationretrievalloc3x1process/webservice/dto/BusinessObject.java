package com.locationretrievalloc3x1process.webservice.dto;

public class BusinessObject {
    private String AddressLine1;
    private String CityName;
    private String StateOrProvinceCode;
    private String CountryCode;
    private String PostalCode;
    private String LocationPlaceCode;

    public String getAddressLine1() { return AddressLine1; }
    public void setAddressLine1(String AddressLine1) { this.AddressLine1 = AddressLine1; }
    public String getCityName() { return CityName; }
    public void setCityName(String CityName) { this.CityName = CityName; }
    public String getStateOrProvinceCode() { return StateOrProvinceCode; }
    public void setStateOrProvinceCode(String StateOrProvinceCode) { this.StateOrProvinceCode = StateOrProvinceCode; }
    public String getCountryCode() { return CountryCode; }
    public void setCountryCode(String CountryCode) { this.CountryCode = CountryCode; }
    public String getPostalCode() { return PostalCode; }
    public void setPostalCode(String PostalCode) { this.PostalCode = PostalCode; }
    public String getLocationPlaceCode() { return LocationPlaceCode; }
    public void setLocationPlaceCode(String LocationPlaceCode) { this.LocationPlaceCode = LocationPlaceCode; }
}
