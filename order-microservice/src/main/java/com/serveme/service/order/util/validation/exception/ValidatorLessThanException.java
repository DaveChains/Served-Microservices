package com.serveme.service.order.util.validation.exception;

import com.serveme.service.order.api.errors.Errors;

import java.math.BigDecimal;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public class ValidatorLessThanException extends ValidatorGenericException {


    public ValidatorLessThanException(String fieldName, BigDecimal value, BigDecimal limitValue) {
        super(Errors.LT_VALIDATION.getError().setField(value.toPlainString()).setValue(limitValue.toPlainString()));
    }

}
