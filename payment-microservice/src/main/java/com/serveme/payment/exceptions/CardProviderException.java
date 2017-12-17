package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.InternalServerErrorException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class CardProviderException extends InternalServerErrorException {

    private final long userId;
    private final String msg;

    public CardProviderException(
            long userId,
            String msg){
        this.userId = userId;
        this.msg = msg;
    }


    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("CARD_PERSISTENCE_EXCEPTION", "Error saving card details")
                .addDetail("userId",userId)
                .addDetail("providerMsg", msg);
    }
}
