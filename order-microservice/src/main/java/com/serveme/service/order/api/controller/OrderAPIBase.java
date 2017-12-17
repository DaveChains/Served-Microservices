package com.serveme.service.order.api.controller;

import com.serveme.service.order.api.errors.Errors;
import com.serveme.service.order.exceptions.*;
import com.serveme.service.order.util.api.httpExceptions.AuthenticationException;
import com.serveme.service.order.util.api.httpExceptions.PaymentRequiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 16/09/16
 */
public abstract class OrderAPIBase {

    protected Logger logger;

    /*******************************************************
     * EXCEPTION HANDLERS
     *******************************************************/


    @ExceptionHandler(OrderCannotBeAcceptedException.class)
    public ResponseEntity<String> handleOrderCannotBeAcceptedException(OrderCannotBeAcceptedException ex) {
        logger.log(Level.SEVERE, "Order cannot be accepted when is " + ex.getCurrentStatus());
        return new ResponseEntity(Errors.ORDER_CANNOT_BE_ACCEPTED.getError().setValue(ex.getCurrentStatus()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeDeclinedException.class)
    public ResponseEntity<String> handleOrderCannotBeDeclinedException(OrderCannotBeDeclinedException ex) {

        logger.log(Level.SEVERE, "Order cannot be accepted when is " + ex.getCurrentStatus());
        return new ResponseEntity(Errors.ORDER_CANNOT_BE_DECLINED.getError().setValue(ex.getCurrentStatus()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderCannotBeFinishedException.class)
    public ResponseEntity<String> handleOrderCannotBeFinishedException(OrderCannotBeFinishedException ex) {

        logger.log(Level.SEVERE, "Order cannot be accepted when is " + ex.getCurrentStatus());
        return new ResponseEntity(Errors.ORDER_CANNOT_BE_FINISHED.getError().setValue(ex.getCurrentStatus()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestaurantNotAuthorizedException.class)
    public ResponseEntity<String> handleRestaurantNotAuthorizedException(RestaurantNotAuthorizedException ex) {

        logger.log(Level.SEVERE, "Restaurant not authorized to access to order " + ex.getOrderId());
        return new ResponseEntity(Errors.RESTAURANT_NOT_AUTHORIZED.getError().setValue(ex.getOrderId()), HttpStatus.FORBIDDEN);

    }


    @ExceptionHandler(OrderAlreadyRatedException.class)
    public ResponseEntity<String> handleOrderAlreadyRatedException(OrderAlreadyRatedException ex) {
        logger.log(Level.SEVERE, "Not invited user trying to review order " + ex.getOrderId());
        return new ResponseEntity(Errors.ORDER_ALREADY_REVIEW_BY_USER.getError().setValue(ex.getOrderId()), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(NotInvitedToOrderException.class)
    public ResponseEntity<String> handleNotInvitedToOrderException(NotInvitedToOrderException ex) {
        logger.log(Level.SEVERE, "Not invited user trying to review order " + ex.getOrderId());
        return new ResponseEntity(Errors.USER_NOT_INVITED_ORDER.getError().setValue(ex.getOrderId()), HttpStatus.FORBIDDEN);

    }


    @ExceptionHandler(CannotReviewOrderYetException.class)
    public ResponseEntity<String> handleCannotReviewOrderYetException(CannotReviewOrderYetException ex) {
        logger.log(Level.SEVERE, "Order cannot be reviewed from status " + ex.getStatus());
        return new ResponseEntity(Errors.ORDER_CANNOT_BE_REVIEWED.getError().setValue(ex.getStatus()), HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        logger.log(Level.SEVERE, "Order " + ex.getOrderId() + " NOT FOUND");
        return new ResponseEntity(Errors.ORDER_NOT_FOUND.getError().setValue(ex.getOrderId()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(OrderTooSmallException.class)
    public ResponseEntity<String> handleOrderTooSmallException(OrderTooSmallException ex) {
        logger.log(Level.SEVERE, "Order too small for restaurant " + ex.getRestaurantId());
        return new ResponseEntity(Errors.ORDER_TOO_SMALL.getError().setValue(ex.getRestaurantId()), HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(RestaurantClosedException.class)
    public ResponseEntity<String> handleRestaurantClosedException(RestaurantClosedException ex) {
        logger.log(Level.SEVERE, "Restaurant " + ex.getRestaurantId() + " Will be closed at " + ex.getArrivalPlusEatingTime());
        return new ResponseEntity(Errors.RESTAURANT_CLOSED.getError().setValue(ex.getRestaurantId()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {

        logger.log(Level.SEVERE, "Restaurant " + ex.getRestaurantId() + " NOT FOUND");
        return new ResponseEntity(Errors.RESTAURANT_NOT_FOUND.getError().setValue(ex.getRestaurantId()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoMenuException.class)
    public ResponseEntity<String> handleNoMenuException(NoMenuException ex) {
        logger.log(Level.SEVERE, "Restaurant " + ex.getRestaurantId() + " NOT FOUND");
        return new ResponseEntity(Errors.RESTAURANT_NO_MENU.getError().setValue(ex.getRestaurantId()), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(NoMatchingDishException.class)
    public ResponseEntity<String> handleNoMatchingDishException(NoMatchingDishException ex) {

        logger.log(Level.SEVERE, "Dish " + ex.getDishId() + " NOT FOUND");
        return new ResponseEntity(Errors.ORDER_NOT_MATCHED_DISH.getError().setValue(ex.getDishId()), HttpStatus.NOT_FOUND);

    }
    
    @ExceptionHandler(DishNotAvailableException.class)
    public ResponseEntity<String> handleDishNotAvailableException(DishNotAvailableException ex) {

        logger.log(Level.SEVERE, "Dish " + ex.getDishId() + " NOT AVAILABLE");
        return new ResponseEntity(Errors.DISH_NOT_AVAILABLE.getError().setValue(ex.getDishId()), HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(ArrivalMinTimeException.class)
    public ResponseEntity<String> handleMinTimeException(ArrivalMinTimeException ex) {

        logger.log(Level.SEVERE, "ERROR: Wrong arrival time(" + ex.getActualTime() + ") cannot be before " + ex.getMinTime());
        return new ResponseEntity(Errors.ORDER_MIN_TIME.getError().setValue(ex.getMinTime()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ArrivalMaxTimeException.class)
    public ResponseEntity<String> handleMaxTimeException(ArrivalMaxTimeException ex) {

        logger.log(Level.SEVERE, "ERROR: Wrong arrival time(" + ex.getActualTime() + ") cannot be after " + ex.getMaxTime());
        return new ResponseEntity(Errors.ORDER_MAX_TIME.getError().setValue(ex.getMaxTime()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {

        logger.log(Level.SEVERE, "ERROR: not authorized" + ex.getMessage());
        return new ResponseEntity(Errors.RESTAURANT_NOT_AUTHORIZED.getError().setValue(ex.getMessage()), HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(PaymentRequiredException.class)
    public ResponseEntity<String> handlePaymentRequiredException(PaymentRequiredException ex) {

        logger.log(Level.SEVERE, "ERROR: payment required" + ex.getMessage());
        return new ResponseEntity(Errors.PAYMENT_REQUIRED.getError().setValue(ex.getMessage()), HttpStatus.PAYMENT_REQUIRED);

    }

    @ExceptionHandler(InvalidDistanceException.class)
    public ResponseEntity<String> handleInvalidDistanceException(InvalidDistanceException ex) {

        logger.log(Level.SEVERE, "ERROR: invalid distance" + ex.getMessage());
        return new ResponseEntity(Errors.INVALID_DISTANCE.getError().setValue(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

}
