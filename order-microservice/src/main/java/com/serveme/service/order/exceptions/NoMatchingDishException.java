package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class NoMatchingDishException extends RuntimeException {


    private final long dishId;

    public NoMatchingDishException(long dishId){
        this.dishId = dishId;

    }

    public long getDishId() {
        return dishId;
    }
}
