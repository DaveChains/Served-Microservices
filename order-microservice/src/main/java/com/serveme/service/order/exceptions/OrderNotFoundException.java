package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderNotFoundException extends RuntimeException {

    private String orderId;

    public OrderNotFoundException(String orderId){
        super();
        this.orderId = orderId;
    }


    public String getOrderId() {
        return orderId;
    }
}
