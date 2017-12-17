package com.serveme.payment.util.error.exceptions;

import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;
import org.springframework.http.HttpStatus;

public class BadRequestException extends ServemeServiceException {
    private static final long serialVersionUID = 1;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public BaseErrorResponse getErrorResponse() {
        return new ErrorResponse("BAD_REQUEST", getMessage());
    }
}
