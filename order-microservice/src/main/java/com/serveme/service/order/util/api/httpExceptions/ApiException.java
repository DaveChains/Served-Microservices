package com.serveme.service.order.util.api.httpExceptions;

import com.serveme.service.order.util.api.error.ApiError;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Davids-iMac on 19/06/15.
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
