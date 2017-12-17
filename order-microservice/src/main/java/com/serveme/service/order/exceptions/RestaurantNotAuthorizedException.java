package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class RestaurantNotAuthorizedException extends RuntimeException {

    private String orderId;

    public RestaurantNotAuthorizedException(String restaurantId){
        super();
        this.orderId = restaurantId;
    }


    public String getOrderId() {
        return orderId;
    }
}
