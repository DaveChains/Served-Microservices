package com.serveme.payment.util.error.handlers;

import com.serveme.payment.util.error.exceptions.ServemeServiceException;
import com.serveme.payment.util.error.responses.BaseErrorResponse;
import com.serveme.payment.util.error.responses.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ServemeGlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ServemeGlobalExceptionHandler.class);



    @ExceptionHandler(ServemeServiceException.class)
    @ResponseBody
    public ResponseEntity handleOtherElderExceptions(ServemeServiceException ex) {
        HttpStatus httpStatus = ex.getHttpStatus();
        BaseErrorResponse errorResponse = ex.getErrorResponse();
        logger.warn(
                "Returning managed error response {}: {} for error {}",
                httpStatus,
                errorResponse,
                ex.toString());
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity handleOtherElderExceptions(Throwable ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        BaseErrorResponse errorResponse = new ErrorResponse(
                "GENERIC_INTERNAL_ERROR",
                ex.getMessage()
        );
        logger.warn(
                "Returning managed error response {}: {} for error {}",
                httpStatus,
                errorResponse,
                ex.toString());
        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

}
