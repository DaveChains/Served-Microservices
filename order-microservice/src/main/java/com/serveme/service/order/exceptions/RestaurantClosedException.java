package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class RestaurantClosedException extends RuntimeException {

    private final long arrivalPlusEatingTime;
    private long restaurantId;

    public RestaurantClosedException(long restaurantId, long arrivalPlusEatingTime) {

        this.restaurantId = restaurantId;
        this.arrivalPlusEatingTime = arrivalPlusEatingTime;
    }

    public long getArrivalPlusEatingTime() {
        return arrivalPlusEatingTime;
    }

    public long getRestaurantId() {
        return restaurantId;
    }
}
