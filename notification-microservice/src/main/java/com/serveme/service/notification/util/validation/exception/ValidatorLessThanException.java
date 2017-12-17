package com.serveme.service.notification.util.validation.exception;

import java.math.BigDecimal;

import com.serveme.service.notification.api.errors.Errors;

/**
 * Created by DavidChains on 26/10/15.
 */
public class ValidatorLessThanException extends ValidatorGenericException {


    public ValidatorLessThanException(String fieldName, BigDecimal value, BigDecimal limitValue) {
        super(Errors.LT_VALIDATION.getError().setField(value.toPlainString()).setValue(limitValue.toPlainString()));
    }

}
