package com.serveme.service.notification.util.validation.exception;

import com.serveme.service.notification.util.api.error.ApiError;
import com.serveme.service.notification.util.api.httpExceptions.ApiException;

/**
 * Created by DavidChains on 26/10/15.
 */
public class ValidatorGenericException extends ApiException {

    public ValidatorGenericException(ApiError error){
        super(error);
    }

    public ValidatorGenericException(String logMessage, ApiError error){
        super(logMessage, error);
    }


}
