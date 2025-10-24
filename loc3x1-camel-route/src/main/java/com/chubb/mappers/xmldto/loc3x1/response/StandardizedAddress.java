package com.chubb.mappers.xmldto.loc3x1.response;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class StandardizedAddress {

    @XmlElement(name = "CityName")
    private String cityName;

    @XmlElement(name = "CountyName")
    private String countyName;

    @XmlElement(name = "StateOrProvinceCode")
    private String stateOrProvinceCode;

    @XmlElement(name = "StateOrProvinceName")
    private String stateOrProvinceName;

    @XmlElement(name = "PostalStateAbbreviation")
    private String postalStateAbbreviation;

    @XmlElement(name = "PostalCode")
    private String postalCode;

    @XmlElement(name = "CountryCode")
    private String countryCode;

    @XmlElement(name = "CountryName")
    private String countryName;

    @XmlElement(name = "LocationPlaceCode")
    private String locationPlaceCode;

    @XmlElement(name = "POBoxIndicator")
    private boolean poBoxIndicator;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getStateOrProvinceCode() {
        return stateOrProvinceCode;
    }

    public void setStateOrProvinceCode(String stateOrProvinceCode) {
        this.stateOrProvinceCode = stateOrProvinceCode;
    }

    public String getStateOrProvinceName() {
        return stateOrProvinceName;
    }

    public void setStateOrProvinceName(String stateOrProvinceName) {
        this.stateOrProvinceName = stateOrProvinceName;
    }

    public String getPostalStateAbbreviation() {
        return postalStateAbbreviation;
    }

    public void setPostalStateAbbreviation(String postalStateAbbreviation) {
        this.postalStateAbbreviation = postalStateAbbreviation;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLocationPlaceCode() {
        return locationPlaceCode;
    }

    public void setLocationPlaceCode(String locationPlaceCode) {
        this.locationPlaceCode = locationPlaceCode;
    }

    public boolean isPoBoxIndicator() {
        return poBoxIndicator;
    }

    public void setPoBoxIndicator(boolean poBoxIndicator) {
        this.poBoxIndicator = poBoxIndicator;
    }

	@Override
	public String toString() {
		return "StandardizedAddress [cityName=" + cityName + ", countyName=" + countyName + ", stateOrProvinceCode="
				+ stateOrProvinceCode + ", stateOrProvinceName=" + stateOrProvinceName + ", postalStateAbbreviation="
				+ postalStateAbbreviation + ", postalCode=" + postalCode + ", countryCode=" + countryCode
				+ ", countryName=" + countryName + ", locationPlaceCode=" + locationPlaceCode + ", poBoxIndicator="
				+ poBoxIndicator + "]";
	}
    
    
}