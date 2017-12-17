package com.serveme.service.searching.exception;

/**
 * Created by Davids-iMac on 30/10/15.
 */
public class RestaurantNotFoundException extends RuntimeException{

    private long restaurantId;

    public RestaurantNotFoundException(long restaurantId){
        super();
        this.restaurantId = restaurantId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }
}
