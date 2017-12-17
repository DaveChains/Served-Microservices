package com.serveme.payment.exceptions;

import com.serveme.payment.enums.PaymentProviderId;
import com.serveme.payment.util.error.exceptions.InternalServerErrorException;
import com.serveme.payment.util.error.exceptions.NotFoundException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

public class PaymentProviderConflictException extends InternalServerErrorException {

    private final long chargeId;


    private final  PaymentProviderId chargeProvider;
    private final PaymentProviderId paymentDetailProvider;
    private final PaymentProviderId systemProvider;

    public PaymentProviderConflictException(
            long chargeId,
            PaymentProviderId chargeProvider,
            PaymentProviderId paymentDetailProvider,
            PaymentProviderId systemProvider
            ){
        super();
        this.chargeId = chargeId;
        this.chargeProvider = chargeProvider;
        this.paymentDetailProvider = paymentDetailProvider;
        this.systemProvider = systemProvider;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("PAYMENT_PROVIDER_CONFLICT",
                "Payment provider conflicts. " +
                "chargeProvider, paymentDetailProvider and system provider " +
                "need to be the same")
                .addDetail("chargeId", chargeId)
                .addDetail("chargeProvider", chargeProvider)
                .addDetail("paymentDetailProvider", paymentDetailProvider)
                .addDetail("systemProvider",systemProvider);
    }
}
