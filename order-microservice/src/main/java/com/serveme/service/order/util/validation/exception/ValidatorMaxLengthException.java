package com.serveme.service.order.util.validation.exception;

import com.serveme.service.order.api.errors.Errors;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorMaxLengthException extends ValidatorGenericException{

    public ValidatorMaxLengthException(String fieldName, Object value, int length){
        super(Errors.MAX_LENGTH_VALIDATION.getError().setField(value.toString()).setValue(length));

    }

}
