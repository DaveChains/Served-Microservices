package com.serveme.service.notification.util.api.httpExceptions;

import com.serveme.service.notification.util.api.error.ApiError;

/**
 * Created by DavidChains on 13/06/15.
 */
public class AuthenticationException extends ApiException {


    public AuthenticationException(String logMessage, ApiError error){
        super(logMessage, error);
    }
}
