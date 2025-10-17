package com.locationretrievalloc3x1process.webservice.model;

import lombok.Data;

@Data
public class LocationRequest {
	private String addressLine1;
	private String cityName;
    private String postalCode;
    private String stateOrProvinceCode;
    private String postalStateAbbreviation;
    private String locationPlaceCode;
    private String countryCode;
    private String countryAbbreviation;
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getStateOrProvinceCode() {
		return stateOrProvinceCode;
	}
	public void setStateOrProvinceCode(String stateOrProvinceCode) {
		this.stateOrProvinceCode = stateOrProvinceCode;
	}
	public String getPostalStateAbbreviation() {
		return postalStateAbbreviation;
	}
	public void setPostalStateAbbreviation(String postalStateAbbreviation) {
		this.postalStateAbbreviation = postalStateAbbreviation;
	}
	public String getLocationPlaceCode() {
		return locationPlaceCode;
	}
	public void setLocationPlaceCode(String locationPlaceCode) {
		this.locationPlaceCode = locationPlaceCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}
	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}
	@Override
	public String toString() {
		return "LocationRequest [addressLine1=" + addressLine1 + ", cityName=" + cityName + ", postalCode=" + postalCode
				+ ", stateOrProvinceCode=" + stateOrProvinceCode + ", postalStateAbbreviation="
				+ postalStateAbbreviation + ", locationPlaceCode=" + locationPlaceCode + ", countryCode=" + countryCode
				+ ", countryAbbreviation=" + countryAbbreviation + "]";
	}
}
