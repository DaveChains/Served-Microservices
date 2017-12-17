package com.serveme.service.order.util.validation.exception;

import com.serveme.service.order.api.errors.Errors;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorNullException extends ValidatorGenericException{


    public ValidatorNullException(String fieldName, Object value) {
        super(Errors.NULL_VALIDATION.getError().setField(fieldName).setValue(value));
    }

}
