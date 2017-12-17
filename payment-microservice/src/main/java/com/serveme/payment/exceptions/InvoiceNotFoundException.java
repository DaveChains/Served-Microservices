package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.NotFoundException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

public class InvoiceNotFoundException extends NotFoundException {

    private final String orderId;

    public InvoiceNotFoundException(String orderId){
        super();
        this.orderId = orderId;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("INVOICE_NOT_FOUND", "Invoice not found")
                .addDetail("orderId", orderId);
    }
}