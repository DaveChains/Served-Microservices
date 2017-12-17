package com.serveme.service.util.exception.http;

/**
 * Created by Davids-iMac on 19/06/15.
 */
public class ForbiddenException extends BaseHTTPException {

    public ForbiddenException(String logMessage, String beautyMessage){
        super(logMessage,beautyMessage);

    }

}
