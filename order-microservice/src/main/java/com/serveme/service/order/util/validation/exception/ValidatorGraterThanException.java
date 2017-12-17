package com.serveme.service.order.util.validation.exception;

import com.serveme.service.order.api.errors.Errors;

import java.math.BigDecimal;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorGraterThanException extends ValidatorGenericException{


    public ValidatorGraterThanException(String fieldName, BigDecimal value, BigDecimal limitValue){
        super(Errors.GT_VALIDATION.getError().setField(value.toPlainString()).setValue(limitValue.toPlainString()));
    }


}
