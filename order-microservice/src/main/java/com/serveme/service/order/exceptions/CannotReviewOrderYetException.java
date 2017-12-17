package com.serveme.service.order.exceptions;

import com.serveme.service.order.enums.OrderStatus;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class CannotReviewOrderYetException extends RuntimeException {

    private OrderStatus status;

    public CannotReviewOrderYetException(OrderStatus status){
        super();
        this.status = status;
    }


    public OrderStatus getStatus() {
        return status;
    }
}
