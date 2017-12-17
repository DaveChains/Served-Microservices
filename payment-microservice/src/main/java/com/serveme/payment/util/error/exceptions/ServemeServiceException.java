package com.serveme.payment.util.error.exceptions;

import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;
import org.springframework.http.HttpStatus;

public abstract class ServemeServiceException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ServemeServiceException() {
    }

    public ServemeServiceException(String message) {
        super(message);
    }

    public ServemeServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServemeServiceException(Throwable cause) {
        super(cause);
    }

    public ServemeServiceException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract HttpStatus getHttpStatus();
    public abstract BaseErrorResponse getErrorResponse();
}
