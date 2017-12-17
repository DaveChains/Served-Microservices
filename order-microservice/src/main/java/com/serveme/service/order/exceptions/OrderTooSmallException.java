package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderTooSmallException extends RuntimeException {

    private long restaurantId;

    public OrderTooSmallException(long restaurantId) {
        super();
        this.restaurantId = restaurantId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }
}
