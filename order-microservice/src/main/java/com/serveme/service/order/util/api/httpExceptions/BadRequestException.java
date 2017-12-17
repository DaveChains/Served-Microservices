package com.serveme.service.order.util.api.httpExceptions;

import com.serveme.service.order.util.api.error.ApiError;

/**
 * Created by Davids-iMac on 19/06/15.
 */
public class BadRequestException extends ApiException {


    public BadRequestException(String logMessage, ApiError error){
        super(logMessage, error);
    }
}
