package com.serveme.service.order.util.validation.exception;

import com.serveme.service.order.api.errors.Errors;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorEmailException extends ValidatorGenericException{


    public ValidatorEmailException(String fieldName, String value) {

        super(Errors.EMAIL_VALIDATION.getError().setField(fieldName).setValue(value));
    }

}
