package com.locationretrievalloc3x1process.webservice.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class StateOrProvinceBack {

    @XmlElement(name = "StateOrProvinceCode")
    private String stateOrProvinceCode;

    @XmlElement(name = "StateOrProvinceAbbreviation")
    private String stateOrProvinceAbbreviation;

    @XmlElement(name = "StateOrProvinceName")
    private String stateOrProvinceName;

    @XmlElement(name = "PostalStateAbbreviation")
    private String postalStateAbbreviation;

    @XmlElement(name = "CountryCode")
    private String countryCode;

	public String getStateOrProvinceCode() {
		return stateOrProvinceCode;
	}

	public void setStateOrProvinceCode(String stateOrProvinceCode) {
		this.stateOrProvinceCode = stateOrProvinceCode;
	}

	public String getStateOrProvinceAbbreviation() {
		return stateOrProvinceAbbreviation;
	}

	public void setStateOrProvinceAbbreviation(String stateOrProvinceAbbreviation) {
		this.stateOrProvinceAbbreviation = stateOrProvinceAbbreviation;
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "StateOrProvince [stateOrProvinceCode=" + stateOrProvinceCode + ", stateOrProvinceAbbreviation="
				+ stateOrProvinceAbbreviation + ", stateOrProvinceName=" + stateOrProvinceName
				+ ", postalStateAbbreviation=" + postalStateAbbreviation + ", countryCode=" + countryCode + "]";
	}

    
}
