package com.serveme.service.exception;

public class NonAuthorizedException extends RuntimeException {

    private String token;

    public NonAuthorizedException(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
