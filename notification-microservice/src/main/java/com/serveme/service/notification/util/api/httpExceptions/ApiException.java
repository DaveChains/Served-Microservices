package com.serveme.service.notification.util.api.httpExceptions;

import java.util.LinkedList;
import java.util.List;

import com.serveme.service.notification.util.api.error.ApiError;

/**
 * Created by DavidChains on 19/06/15.
 */
public abstract class ApiException extends RuntimeException{


    private List<ApiError> errors = new LinkedList<>();



    public ApiException(String logMessage, ApiError error){
        super(logMessage);
        addError(error);
    }

    public ApiException(ApiError error){
        super(error.toString());
        addError(error);
    }


    public ApiException addError(ApiError error){
        errors.add(error);
        return this;
    }

    public List<ApiError> getErrors() {
        return errors;
    }
}
