package com.serveme.service.order.util.validation.exception;

import com.serveme.service.order.util.api.error.ApiError;
import com.serveme.service.order.util.api.httpExceptions.ApiException;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorGenericException extends ApiException {

    public ValidatorGenericException(ApiError error){
        super(error);
    }

    public ValidatorGenericException(String logMessage, ApiError error){
        super(logMessage, error);
    }


}
