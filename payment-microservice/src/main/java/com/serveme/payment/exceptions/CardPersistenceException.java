package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.InternalServerErrorException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class CardPersistenceException extends InternalServerErrorException {

    private final long userId;
    private final String token;

    public CardPersistenceException(long userId, String token){
        this.userId = userId;
        this.token = token;
    }



    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("CARD_PERSISTENCE_EXCEPTION", "Error saving card details")
                .addDetail("userId",userId)
                .addDetail("token", token);
    }
}
