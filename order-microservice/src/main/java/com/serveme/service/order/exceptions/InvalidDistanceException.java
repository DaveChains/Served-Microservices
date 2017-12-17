package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 29/11/15.
 */
public class InvalidDistanceException extends RuntimeException {

    private long restaurantId;

    public InvalidDistanceException(long restaurantId) {
        super();
        this.restaurantId = restaurantId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
