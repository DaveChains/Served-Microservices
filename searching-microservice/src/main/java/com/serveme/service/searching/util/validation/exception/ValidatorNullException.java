package com.serveme.service.searching.util.validation.exception;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorNullException extends ValidatorGenericException{


    public ValidatorNullException(String fieldName, Object value) {
        super(fieldName, value);
    }

    @Override
    public String getMessage(){
        return this.fieldName + " value is null";
    }
}
