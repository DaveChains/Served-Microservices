package com.serveme.service.order.util.api.httpExceptions;

import com.serveme.service.order.util.api.error.ApiError;

/**
 * Created by Davids-iMac on 13/06/15.
 */
public class NotFoundException extends ApiException {

    public NotFoundException(String logMessage, ApiError error){
        super(logMessage, error);

    }
}
