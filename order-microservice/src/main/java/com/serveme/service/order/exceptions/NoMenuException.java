package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class NoMenuException extends RuntimeException {

    private long restaurantId;

    public NoMenuException(long restaurantId){
        super();
        this.restaurantId = restaurantId;
    }


    public long getRestaurantId() {
        return restaurantId;
    }
    
}
