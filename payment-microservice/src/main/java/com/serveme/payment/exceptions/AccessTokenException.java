package com.serveme.payment.exceptions;

import com.serveme.payment.util.error.exceptions.UnauthorizedException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class AccessTokenException extends UnauthorizedException {

    private final String token;

    public AccessTokenException(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("UNAUTHORIZED", "Wrong token")
                .addDetail("token",token);
    }

}
