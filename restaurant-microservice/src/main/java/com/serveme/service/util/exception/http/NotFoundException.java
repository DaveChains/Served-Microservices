package com.serveme.service.util.exception.http;

/**
 * Created by Davids-iMac on 13/06/15.
 */
public class NotFoundException extends BaseHTTPException {

    public NotFoundException(String logMessage, String beautyMessage){
        super(logMessage,beautyMessage);

    }
}
