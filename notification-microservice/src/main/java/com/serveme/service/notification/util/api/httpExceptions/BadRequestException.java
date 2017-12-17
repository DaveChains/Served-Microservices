package com.serveme.service.notification.util.api.httpExceptions;

import com.serveme.service.notification.util.api.error.ApiError;

/**
 * Created by DavidChains on 19/06/15.
 */
public class BadRequestException extends ApiException {


    public BadRequestException(String logMessage, ApiError error){
        super(logMessage, error);
    }
}
