package com.serveme.service.util.exception.http;

/**
 * Created by Davids-iMac on 19/06/15.
 */
public class BadRequestException extends BaseHTTPException {

    public BadRequestException(String logMessage, String beautyMessage){
        super(logMessage,beautyMessage);

    }

}
