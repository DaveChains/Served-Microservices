package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 29/11/15.
 */
public class ArrivalMinTimeException extends RuntimeException{

    private long minTime;
    private long actualTime;


    public ArrivalMinTimeException(long minTime, long actualTime){
        super();
        this.minTime = minTime;
        this.actualTime = actualTime;
    }

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public long getActualTime() {
        return actualTime;
    }

    public void setActualTime(long actualTime) {
        this.actualTime = actualTime;
    }
}