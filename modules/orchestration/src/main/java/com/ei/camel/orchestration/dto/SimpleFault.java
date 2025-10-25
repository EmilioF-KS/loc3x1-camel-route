package com.ei.camel.orchestration.dto;

public class SimpleFault {
    private String faultMessageText;
    private String errorCode;
    private String source;
    private String correlationId;
    private String timestamp;

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

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SimpleFault{" +
                "faultMessageText='" + faultMessageText + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", source='" + source + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}