package com.serveme.payment.util.error.responses;

public final class ErrorResponse extends BaseErrorResponse {
    public static final String RESPONSE_TYPE_MARKER = "SERVEME_ERROR";
    private static final long serialVersionUID = 1;


    public ErrorResponse(String errorCode, String message) {
        super(RESPONSE_TYPE_MARKER,
                errorCode,
                message);
    }


}
