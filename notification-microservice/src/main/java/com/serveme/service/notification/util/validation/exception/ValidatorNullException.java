package com.serveme.service.notification.util.validation.exception;

import com.serveme.service.notification.api.errors.Errors;

/**
 * Created by DavidChains on 26/10/15.
 */
public class ValidatorNullException extends ValidatorGenericException{


    public ValidatorNullException(String fieldName, Object value) {
        super(Errors.NULL_VALIDATION.getError().setField(fieldName).setValue(value));
    }

}
