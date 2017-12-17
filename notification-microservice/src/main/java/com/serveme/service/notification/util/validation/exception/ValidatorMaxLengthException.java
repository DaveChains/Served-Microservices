package com.serveme.service.notification.util.validation.exception;

import com.serveme.service.notification.api.errors.Errors;

/**
 * Created by DavidChains on 26/10/15.
 */
public class ValidatorMaxLengthException extends ValidatorGenericException{

    public ValidatorMaxLengthException(String fieldName, Object value, int length){
        super(Errors.MAX_LENGTH_VALIDATION.getError().setField(value.toString()).setValue(length));

    }

}
