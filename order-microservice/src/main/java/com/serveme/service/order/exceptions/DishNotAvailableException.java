package com.serveme.service.order.exceptions;

/**
 * Created by DavidChains.
 */
public class DishNotAvailableException extends RuntimeException {
    private final long dishId;

    public DishNotAvailableException(long dishId){
        this.dishId = dishId;

    }

    public long getDishId() {
        return dishId;
    }
}
