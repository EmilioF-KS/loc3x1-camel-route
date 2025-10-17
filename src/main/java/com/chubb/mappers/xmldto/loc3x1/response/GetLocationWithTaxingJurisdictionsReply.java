package com.chubb.mappers.xmldto.loc3x1.response;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "GetLocationWithTaxingJurisdictionsReply")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetLocationWithTaxingJurisdictionsReply {

    @XmlElement(name = "Location")
    private Location location;

    @XmlElement(name = "StatusInformation")
    private StatusInformation statusInformation;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public StatusInformation getStatusInformation() {
        return statusInformation;
    }

    public void setStatusInformation(StatusInformation statusInformation) {
        this.statusInformation = statusInformation;
    }

	@Override
	public String toString() {
		return "GetLocationWithTaxingJurisdictionsReply [location=" + location + ", statusInformation="
				+ statusInformation + "]";
	}
    
    
}