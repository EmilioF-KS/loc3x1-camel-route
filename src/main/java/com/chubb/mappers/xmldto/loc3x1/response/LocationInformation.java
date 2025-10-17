package com.chubb.mappers.xmldto.loc3x1.response;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class LocationInformation {

    @XmlElement(name = "CityCode")
    private String cityCode;

    @XmlElement(name = "CountyCode")
    private String countyCode;

    @XmlElement(name = "LicenseCode")
    private String licenseCode;

    @XmlElement(name = "FireDistrictCode")
    private String fireDistrictCode;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getFireDistrictCode() {
        return fireDistrictCode;
    }

    public void setFireDistrictCode(String fireDistrictCode) {
        this.fireDistrictCode = fireDistrictCode;
    }

	@Override
	public String toString() {
		return "LocationInformation [cityCode=" + cityCode + ", countyCode=" + countyCode + ", licenseCode="
				+ licenseCode + ", fireDistrictCode=" + fireDistrictCode + "]";
	}
    
    
}