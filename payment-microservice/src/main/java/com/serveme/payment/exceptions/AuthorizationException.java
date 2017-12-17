package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.UnauthorizedException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class AuthorizationException extends UnauthorizedException {

    private final long userId;

    public AuthorizationException(long userId){
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }


    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("UNAUTHORIZED", "User not authorized")
                .addDetail("userId",userId);
    }
}
