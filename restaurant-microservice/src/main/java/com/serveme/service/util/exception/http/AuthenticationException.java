package com.serveme.service.util.exception.http;

/**
 * Created by Davids-iMac on 13/06/15.
 */
public class AuthenticationException extends BaseHTTPException {

    public AuthenticationException(String logMessage, String beautyMessage){
        super(logMessage,beautyMessage);

    }
}
