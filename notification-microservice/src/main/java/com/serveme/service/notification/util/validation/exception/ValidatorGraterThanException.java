package com.serveme.service.notification.util.validation.exception;

import java.math.BigDecimal;

import com.serveme.service.notification.api.errors.Errors;

/**
 * Created by DavidChains on 26/10/15.
 */
public class ValidatorGraterThanException extends ValidatorGenericException{


    public ValidatorGraterThanException(String fieldName, BigDecimal value, BigDecimal limitValue){
        super(Errors.GT_VALIDATION.getError().setField(value.toPlainString()).setValue(limitValue.toPlainString()));
    }


}
