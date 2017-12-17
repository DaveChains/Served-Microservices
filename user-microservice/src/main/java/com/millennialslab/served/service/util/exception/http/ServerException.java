package com.millennialslab.served.service.util.exception.http;

/**
 * Created by Davids-iMac on 19/06/15.
 */
public class ServerException extends BaseHTTPException {

    public ServerException(String logMessage, String beautyMessage){
        super(logMessage,beautyMessage);

    }
}
