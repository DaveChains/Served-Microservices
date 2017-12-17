package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.InternalServerErrorException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class InvoicePersistenceException extends InternalServerErrorException {

    private final String orderId;

    public InvoicePersistenceException(String orderId, Throwable cause){
        super(cause.getMessage(), cause);
        this.orderId = orderId;
    }



    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("INVOICE_PERSISTENCE", "Error saving invoice for given order")
                .addDetail("orderId", orderId)
                .addDetail("exceptionDetail", getMessage());
    }
}
