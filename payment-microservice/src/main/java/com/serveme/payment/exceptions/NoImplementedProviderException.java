package com.serveme.payment.exceptions;

import com.serveme.payment.enums.PaymentProviderId;
import com.serveme.payment.util.error.exceptions.BadRequestException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

public class NoImplementedProviderException extends BadRequestException {

    private final PaymentProviderId paymentProviderId;

    public NoImplementedProviderException(PaymentProviderId paymentProviderId){
        super();
        this.paymentProviderId = paymentProviderId;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("NO_IMPLEMENTED_PAYMENT_PROVIDER", "There is no implementation for this paymentProvider id")
                .addDetail("paymentProviderId", paymentProviderId);
    }
}
