package com.serveme.service.util.validation.exception;

import java.math.BigDecimal;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorLessThanException extends ValidatorGenericException{

    private final BigDecimal limitValue;

    public ValidatorLessThanException(String fieldName, BigDecimal value, BigDecimal limitValue){
        super(fieldName, value);
        this.limitValue = limitValue;
    }

    @Override
    public String getMessage(){
        return this.fieldName + " value "+(value!=null?value.toString():"NULL")+" should be less than "+limitValue;
    }
}
