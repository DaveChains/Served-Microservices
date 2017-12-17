package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.InternalServerErrorException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

public class TooManyTriesException extends InternalServerErrorException {

    private final long chargeId;
    private int tries;

    public TooManyTriesException(
            long chargeId,
            int tries){
        super();
        this.chargeId = chargeId;
        this.tries = tries;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("TOO_MANY_TRIES", "Too many tries for this charge "+chargeId)
                .addDetail("chargeId", chargeId)
                .addDetail("tries", tries);
    }
}
