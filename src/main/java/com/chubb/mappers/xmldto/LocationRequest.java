package com.chubb.mappers.xmldto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LocationRequest", namespace = "http://ei/location/location_request_loc3x1b")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationRequest {

    @XmlElement(name = "AddressLine1")
    private String addressLine1;

    @XmlElement(name = "CityName")
    private String cityName;

    @XmlElement(name = "PostalStateAbbreviation")
    private String postalStateAbbreviation;

    @XmlElement(name = "PostalCode")
    private String postalCode;

    @XmlElement(name = "CountryCode")
    private String countryCode;
    
    @XmlElement(name = "LocationPlaceCode")
    private String locationPlaceCode;

    @XmlElement(name = "StateOrProvinceCode")
    private String stateOrProvinceCode;

    @XmlElement(name = "CountryAbbreviation")
    private String countryAbbreviation;
    
	public String getAddressLine1() {
		System.out.println("getAddressLine1 : " + addressLine1);
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		System.out.println("setAddressLine1 : " + addressLine1);
		this.addressLine1 = addressLine1;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getLocationPlaceCode() {
		return locationPlaceCode;
	}

	public void setLocationPlaceCode(String locationPlaceCode) {
		this.locationPlaceCode = locationPlaceCode;
	}

	public String getStateOrProvinceCode() {
		return stateOrProvinceCode;
	}

	public void setStateOrProvinceCode(String stateOrProvinceCode) {
		this.stateOrProvinceCode = stateOrProvinceCode;
	}

	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}

	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}
}
