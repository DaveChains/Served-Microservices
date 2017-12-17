package com.serveme.service.notification.util.api.httpExceptions;

import com.serveme.service.notification.util.api.error.ApiError;

/**
 * Created by DavidChains on 13/06/15.
 */
public class NotFoundException extends ApiException {

    public NotFoundException(String logMessage, ApiError error){
        super(logMessage, error);

    }
}
