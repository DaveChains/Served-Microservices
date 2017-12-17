package com.serveme.service.notification.util.http;

/**
 * Created by DavidChains on 31/10/15.
 */
public class HttpServiceException extends RuntimeException {

    private int statusCode;

    public HttpServiceException(int statusCode, String message){
        super(message);
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return statusCode;
    }


}
