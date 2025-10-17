package com.chubb.converter.util;

import javax.xml.bind.annotation.*;

import com.chubb.xsd.ei.core.stateorprovince.StateOrProvince;

@XmlRootElement(name = "GetStateOrProvinceResponse", namespace = "http://ei/corporate/state_or_province_retrieval_crp11x1/")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetStateOrProvinceResponse {

    @XmlElement(name = "StateOrProvince", namespace = "http://ei/corporate/state_or_province_retrieval_crp11x1/")
    private StateOrProvince stateOrProvince;

	public StateOrProvince getStateOrProvince() {
		return stateOrProvince;
	}

	public void setStateOrProvince(StateOrProvince stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}

	@Override
	public String toString() {
		return "GetStateOrProvinceResponse [stateOrProvince=" + stateOrProvince + "]";
	}

    
}
