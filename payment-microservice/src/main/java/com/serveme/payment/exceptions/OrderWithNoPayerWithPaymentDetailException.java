package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.BadRequestException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

public class OrderWithNoPayerWithPaymentDetailException extends BadRequestException {

    private final String orderId;

    public OrderWithNoPayerWithPaymentDetailException(String orderId){
        super();
        this.orderId = orderId;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("NO_GUEST_WITH_PAYMENT_DETAIL", "There is no payer for this order")
                .addDetail("orderId",orderId);
    }
}
