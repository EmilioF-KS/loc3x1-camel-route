package com.chubb.mappers.xmldto.loc3x1.response;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaxingJurisdiction {

    @XmlElement(name = "TaxingJurisdictionTypeName")
    private String taxingJurisdictionTypeName;

    @XmlElement(name = "TaxingJurisdictionCode")
    private String taxingJurisdictionCode;

    @XmlElement(name = "TaxingJurisdictionName")
    private String taxingJurisdictionName;

    public String getTaxingJurisdictionTypeName() {
        return taxingJurisdictionTypeName;
    }

    public void setTaxingJurisdictionTypeName(String taxingJurisdictionTypeName) {
        this.taxingJurisdictionTypeName = taxingJurisdictionTypeName;
    }

    public String getTaxingJurisdictionCode() {
        return taxingJurisdictionCode;
    }

    public void setTaxingJurisdictionCode(String taxingJurisdictionCode) {
        this.taxingJurisdictionCode = taxingJurisdictionCode;
    }

    public String getTaxingJurisdictionName() {
        return taxingJurisdictionName;
    }

    public void setTaxingJurisdictionName(String taxingJurisdictionName) {
        this.taxingJurisdictionName = taxingJurisdictionName;
    }

	@Override
	public String toString() {
		return "TaxingJurisdiction [taxingJurisdictionTypeName=" + taxingJurisdictionTypeName
				+ ", taxingJurisdictionCode=" + taxingJurisdictionCode + ", taxingJurisdictionName="
				+ taxingJurisdictionName + "]";
	}
    
    
}