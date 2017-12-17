package com.serveme.service.api.controller;

import com.serveme.service.exception.NonAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RestaurantAPIBase {

    protected Logger logger;

    /*******************************************************
     * EXCEPTION HANDLERS
     *******************************************************/

    @ExceptionHandler(NonAuthorizedException.class)
    public ResponseEntity<String> handleAuthorizationException(NonAuthorizedException ex) {
        logger.log(Level.SEVERE, "A session has expired for token: " + ex.getToken());
        return new ResponseEntity<String>("Session Expired", HttpStatus.UNAUTHORIZED);
    }
}
