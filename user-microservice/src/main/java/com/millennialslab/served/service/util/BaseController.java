package com.millennialslab.served.service.util;

import com.millennialslab.served.service.util.exception.http.*;
import com.millennialslab.served.service.util.validation.exception.ValidatorGenericException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 13/06/15.
 */
public class BaseController {

    Logger logger = Logger.getLogger(BaseController.class.getName());

    @ExceptionHandler(ValidatorGenericException.class)
    public ResponseEntity<String> handleValidationException(ValidatorGenericException ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {

        logger.log(Level.SEVERE, ex.getLogMessage());
        return new ResponseEntity( ex.getBeautyMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<String> handleServerException(ServerException ex) {

        logger.log(Level.SEVERE, ex.getLogMessage());
        return new ResponseEntity( ex.getBeautyMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {

        logger.log(Level.SEVERE, ex.getLogMessage());
        return new ResponseEntity( ex.getBeautyMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException ex){
        logger.log(Level.SEVERE, ex.getLogMessage());
        return new ResponseEntity( ex.getBeautyMessage(), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex){
        logger.log(Level.SEVERE, ex.getLogMessage());
        return new ResponseEntity( ex.getBeautyMessage(), HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity("Generic Error processing the request", HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleGenericThrowable(Throwable ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity("Generic Error processing the request", HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
