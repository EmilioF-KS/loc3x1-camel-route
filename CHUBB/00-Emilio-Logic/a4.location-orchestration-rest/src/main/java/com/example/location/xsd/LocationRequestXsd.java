
package com.example.location.xsd;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "LocationRequest")
public class LocationRequestXsd {
    @JacksonXmlProperty(localName = "AddressLine1")
    private String addressLine1;
    @JacksonXmlProperty(localName = "AddressLine2")
    private String addressLine2;
    @JacksonXmlProperty(localName = "CityName")
    private String cityName;
    @JacksonXmlProperty(localName = "StateOrProvinceCode")
    private String stateOrProvinceCode;
    @JacksonXmlProperty(localName = "PostalStateAbbreviation")
    private String postalStateAbbreviation;
    @JacksonXmlProperty(localName = "PostalCode")
    private String postalCode;
    @JacksonXmlProperty(localName = "LocationPlaceCode")
    private String locationPlaceCode;
    @JacksonXmlProperty(localName = "CountryCode")
    private String countryCode;
    @JacksonXmlProperty(localName = "CountryAbbreviation")
    private String countryAbbreviation;
    @JacksonXmlProperty(localName = "SearchStartDate")
    private String searchStartDate;
}
