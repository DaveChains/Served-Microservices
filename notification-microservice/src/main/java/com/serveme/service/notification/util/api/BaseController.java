package com.serveme.service.notification.util.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.serveme.service.notification.util.api.error.ApiError;
import com.serveme.service.notification.util.api.httpExceptions.*;
import com.serveme.service.notification.util.validation.exception.ValidatorGenericException;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by DavidChains on 13/06/15.
 */
public class BaseController {

    Logger logger = Logger.getLogger(BaseController.class.getName());

    @ExceptionHandler(ValidatorGenericException.class)
    public ResponseEntity<String> handleValidationException(ValidatorGenericException ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity(ex.getErrors(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity( ex.getErrors(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<String> handleServerException(InternalServerException ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity( ex.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity( ex.getErrors(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException ex){
        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity( ex.getErrors(), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex){
        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity( ex.getErrors(), HttpStatus.NOT_FOUND);

    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        List<ApiError> errors = new LinkedList<>();
        errors.add(new ApiError(1, "","", "Generic error processing the request"));
        return new ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleGenericThrowable(Throwable ex) {

        logger.log(Level.SEVERE, ex.getMessage());
        List<ApiError> errors = new LinkedList<>();
        errors.add(new ApiError(1,"","","Generic error processing the request"));
        return new ResponseEntity(errors, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
