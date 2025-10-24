package com.chubb.mappers.xmldto.loc3x1.response;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Location {

    @XmlElement(name = "LocationInformation")
    private LocationInformation locationInformation;

    @XmlElement(name = "StandardizedAddress")
    private StandardizedAddress standardizedAddress;

    @XmlElement(name = "TaxingJurisdiction")
    private List<TaxingJurisdiction> taxingJurisdictions;

    public LocationInformation getLocationInformation() {
        return locationInformation;
    }

    public void setLocationInformation(LocationInformation locationInformation) {
        this.locationInformation = locationInformation;
    }

    public StandardizedAddress getStandardizedAddress() {
        return standardizedAddress;
    }

    public void setStandardizedAddress(StandardizedAddress standardizedAddress) {
        this.standardizedAddress = standardizedAddress;
    }

    public List<TaxingJurisdiction> getTaxingJurisdictions() {
        return taxingJurisdictions;
    }

    public void setTaxingJurisdictions(List<TaxingJurisdiction> taxingJurisdictions) {
        this.taxingJurisdictions = taxingJurisdictions;
    }

	@Override
	public String toString() {
		return "Location [locationInformation=" + locationInformation + ", standardizedAddress=" + standardizedAddress
				+ ", taxingJurisdictions=" + taxingJurisdictions + "]";
	}
    
    
}