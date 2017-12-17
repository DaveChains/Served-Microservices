package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.NotFoundException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

public class CardNotFoundException extends NotFoundException {

    private final long userId;

    public CardNotFoundException(long userId){
        super();
        this.userId = userId;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("CARD_NOT_FOUND", "User's card not found")
                .addDetail("userId", userId);
    }
}
