package com.serveme.service.notification.util.validation.exception;

import com.serveme.service.notification.api.errors.Errors;

/**
 * Created by DavidChains on 26/10/15.
 */
public class ValidatorMinLengthException extends ValidatorGenericException{


    public ValidatorMinLengthException(String fieldName, Object value, int length){
        super(Errors.MIN_LENGTH_VALIDATION.getError().setField(value.toString()).setValue(length));

    }
}
