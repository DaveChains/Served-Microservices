package com.serveme.service.order.enums;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 07/06/15.
 */
public enum OrderStatus implements Serializable {

    /**
     * Once the order has been sent by the user, it will be in "Pending" until the restaurant accepts/declines it
     */
    PENDING,

    /**
     * The user accepted the order
     */
    ACCEPTED,

    /**
     * The restaurant declined the order
     */
    DECLINED,

    /**
     * The user cancelled the order
     */
    CANCELLED,

    /**
     * The order has been finished
     */
    FINISHED,
    
    /**
     * The bill has been seen by the user
     */
    CLIENT_REVIEWED,
    
    /**
     * The order has been autocanceled by the system because there is no response from restaurant.
     */
    NO_ANSWER
    ;


}
