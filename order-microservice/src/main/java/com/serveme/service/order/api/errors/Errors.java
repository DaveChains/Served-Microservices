package com.serveme.service.order.api.errors;

import com.serveme.service.order.util.api.error.ApiError;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public enum Errors {


    //USER ERRORS
    AUTHENTICATION(new ApiError(101, null, null, "Authentication Errors: Access token wrong")),

    //RESTAURANT ERRORS
    RESTAURANT_NOT_FOUND(new ApiError(301, "restaurant", "id", "Restaurant not found")),
    RESTAURANT_NO_MENU(new ApiError(302, "restaurant", "id", "Not menu in restaurant")),
    RESTAURANT_CLOSED(new ApiError(303, "restaurant", "id", "Restaurant closed at arrival time")),

    //ORDER ERROR
    ACTIVE_ORDER(new ApiError(502, null, null, "User has already an active order")),
    ORDER_NOT_MATCHED_DISH(new ApiError(503, "dish", "dishId", "Dish not found in the menu")),
    DISH_NOT_AVAILABLE(new ApiError(518, "dish", "dishId", "Dish not available")),
    ORDER_WITHOUT_ITEMS(new ApiError(504, "items", "empty", "No items selected for the order")),
    ORDER_MIN_TIME(new ApiError(505, "time", "min time", "cannot be before than")),
    ORDER_MAX_TIME(new ApiError(506, "time", "max time", "cannot be later than")),
    ORDER_NOT_FOUND(new ApiError(507, "Order", null, "Not found")),
    RESTAURANT_NOT_AUTHORIZED(new ApiError(508, "Order", null, "Restaurant not authorize")),
    ORDER_CANNOT_BE_ACCEPTED(new ApiError(509, "Order status", null, "Order cannot be accepted from the current status")),
    ORDER_CANNOT_BE_DECLINED(new ApiError(510, "Order status", null, "Order cannot be declined from the current status")),
    ORDER_CANNOT_BE_FINISHED(new ApiError(511, "Order status", null, "Order cannot be finished from the current status")),
    ORDER_CANNOT_BE_REVIEWED(new ApiError(512, "Order status", null, "Order cannot be reviewed from the current status")),
    USER_NOT_INVITED_ORDER(new ApiError(513, "Order", null, "User is not invited to the order")),
    ORDER_ALREADY_REVIEW_BY_USER(new ApiError(514, "Order", null, "User cannot review an order twice")),
    INVITATIONS_EXCEED(new ApiError(515, "Invitation", null, "Number of invitations exceed")),
    ORDER_TOO_SMALL(new ApiError(516, "Order", null, "Too small")),
    PAYMENT_REQUIRED(new ApiError(517, "Order", null, "A payment method is required")),
    INVALID_DISTANCE(new ApiError(518, "Order", null, "The user is too far from the restaurant")),


    //GENERIC ERRORS
    INTERNAL_SERVER_ERROR(new ApiError(1, null, null, "Internal Server Error")),
    EMAIL_VALIDATION(new ApiError(3, null, null, "Email Validation error")),
    GT_VALIDATION(new ApiError(4, null, null, "Error Validation: Value grater than")),
    LT_VALIDATION(new ApiError(5, null, null, "Error Validation: Value smaller than")),
    MAX_LENGTH_VALIDATION(new ApiError(6, null, null, "Error Validation: Length larger than")),
    MIN_LENGTH_VALIDATION(new ApiError(7, null, null, "Error Validation: Length smaller than")),
    NULL_VALIDATION(new ApiError(8, null, null, "Error Validation: Null value"));


    private ApiError value;

    Errors(ApiError value) {
        this.value = value;
    }

    public ApiError getError() {
        ApiError copy = new ApiError();
        copy.setCode(value.getCode());
        copy.setField(value.getField());
        copy.setValue(value.getValue());
        copy.setMessage(value.getMessage());
        return copy;
    }
}
