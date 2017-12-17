package com.serveme.payment.util.error.responses;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseErrorResponse implements Serializable {

    private static final long serialVersionUID = 1;
    private final String responseType; // To identify managed serveme response error
    private final String timestamp;
    private final String errorCode;
    private final String message;
    private final Map<String, Object> errorDetails;


    public BaseErrorResponse(String responseType, String errorCode, String message) {
        this.responseType = responseType;
        this.timestamp = Instant.now().toString();
        this.errorCode = errorCode;
        this.message = message;
        this.errorDetails = new HashMap<>();
    }

    public String getResponseType() {
        return responseType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getErrorDetails() {
        return errorDetails;
    }

    public BaseErrorResponse addDetail(String key, Object value){
        errorDetails.put(key, value);
        return this;
    }


    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp='" + timestamp + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
