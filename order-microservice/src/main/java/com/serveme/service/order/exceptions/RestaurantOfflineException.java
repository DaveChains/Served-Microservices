package com.serveme.service.order.exceptions;

public class RestaurantOfflineException extends RuntimeException {

    private long restaurantId;

    public RestaurantOfflineException(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }
}
