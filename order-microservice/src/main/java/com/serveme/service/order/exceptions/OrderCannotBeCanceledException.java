package com.serveme.service.order.exceptions;

import com.serveme.service.order.enums.OrderStatus;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderCannotBeCanceledException extends RuntimeException {

    private OrderStatus currentStatus;

    public OrderCannotBeCanceledException(OrderStatus status) {
        super();
        this.currentStatus = status;
    }


    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }
}
