package com.serveme.payment.util.error.exceptions;

import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;
import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends ServemeServiceException {
    private static final long serialVersionUID = 1;

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }

    public InternalServerErrorException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("INTERNAL_SERVER_ERROR", getMessage());
    }
}
