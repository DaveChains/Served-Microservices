package com.serveme.service.order.util.http;

/**
 * Created by Davids-iMac on 31/10/15.
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
