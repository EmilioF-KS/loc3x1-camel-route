package com.chubb.mappers.xmldto.loc3x1.response;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatusInformation {

    @XmlElement(name = "StatusCode")
    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

	@Override
	public String toString() {
		return "StatusInformation [statusCode=" + statusCode + "]";
	}
    
    
}