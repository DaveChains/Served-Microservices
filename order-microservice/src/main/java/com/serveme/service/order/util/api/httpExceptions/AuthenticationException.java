package com.serveme.service.order.util.api.httpExceptions;

import com.serveme.service.order.util.api.error.ApiError;

/**
 * Created by Davids-iMac on 13/06/15.
 */
public class AuthenticationException extends ApiException {

    public AuthenticationException(String logMessage, ApiError error){
        super(logMessage, error);
    }
}
