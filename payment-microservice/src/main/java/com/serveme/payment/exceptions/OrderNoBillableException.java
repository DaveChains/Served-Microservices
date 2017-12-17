package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.BadRequestException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

public class OrderNoBillableException extends BadRequestException {

    private final String orderId;
    private int guests;

    public OrderNoBillableException(
            String orderId,
            int guests){
        super();
        this.orderId = orderId;
        this.guests = guests;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("NO_GUEST_FOR_ORDER", "There is no payer for this order")
                .addDetail("orderId",orderId)
                .addDetail("guests", guests);
    }
}
