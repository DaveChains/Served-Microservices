package com.serveme.service.order.util.api.httpExceptions;

import com.serveme.service.order.util.api.error.ApiError;

/**
 * Created by Davids-iMac on 19/06/15.
 */
public class InternalServerException extends ApiException {

    public InternalServerException(String logMessage, ApiError error){
        super(logMessage, error);
    }
}
