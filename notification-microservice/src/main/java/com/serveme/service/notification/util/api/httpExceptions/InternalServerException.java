package com.serveme.service.notification.util.api.httpExceptions;

import com.serveme.service.notification.util.api.error.ApiError;

/**
 * Created by DavidChains on 19/06/15.
 */
public class InternalServerException extends ApiException {

    public InternalServerException(String logMessage, ApiError error){
        super(logMessage, error);
    }
}
