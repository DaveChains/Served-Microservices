package com.serveme.service.searching.util.validation.exception;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorGenericException extends RuntimeException{

    protected  final String fieldName;
    protected final Object value;

    public ValidatorGenericException(String fieldName, Object value){
        this.fieldName = fieldName;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

}
