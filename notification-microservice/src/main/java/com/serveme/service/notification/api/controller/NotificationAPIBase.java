package com.serveme.service.notification.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.serveme.service.notification.api.errors.Errors;
import com.serveme.service.notification.exceptions.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DavidChains on 08/12/15.
 */
public abstract class NotificationAPIBase {

    protected Logger logger;

    /*******************************************************
     * EXCEPTION HANDLERS
     *******************************************************/


    @ExceptionHandler(NotificationCannotBeSentException.class)
    public ResponseEntity<String> handleNotificationCannotBeSentException(NotificationCannotBeSentException ex) {
        logger.log(Level.SEVERE, "A push notification cannot be sent to "+ ex.getDeviceId());
        return new ResponseEntity( Errors.PUSH_CANNOT_BE_SENT.getError().setValue(ex.getCurrentStatus()), HttpStatus.BAD_REQUEST);
    }


}
