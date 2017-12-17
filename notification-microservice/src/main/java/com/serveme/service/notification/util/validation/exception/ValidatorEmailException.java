package com.serveme.service.notification.util.validation.exception;

import com.serveme.service.notification.api.errors.Errors;

/**
 * Created by DavidChains on 26/10/15.
 */
public class ValidatorEmailException extends ValidatorGenericException{


    public ValidatorEmailException(String fieldName, String value) {

        super(Errors.EMAIL_VALIDATION.getError().setField(fieldName).setValue(value));
    }

}
