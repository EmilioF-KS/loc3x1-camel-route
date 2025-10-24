package com.locationretrievalloc3x1process.webservice.dto;

public class SimpleFault {
    private String faultMessageText;
    private String errorCode;
    private String source;

    // Getters and setters
    public String getFaultMessageText() {
        return faultMessageText;
    }

    public void setFaultMessageText(String faultMessageText) {
        this.faultMessageText = faultMessageText;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	@Override
	public String toString() {
		return "SimpleFault [faultMessageText=" + faultMessageText + ", errorCode=" + errorCode + ", source=" + source
				+ "]";
	}
}