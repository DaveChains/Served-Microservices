package com.serveme.service.order.exceptions;

/**
 * Created by Davids-iMac on 29/11/15.
 */
public class ArrivalMaxTimeException extends RuntimeException{

    private long maxTime;
    private long actualTime;


    public ArrivalMaxTimeException(long maxTime, long actualTime){
        super();
        this.maxTime = maxTime;
        this.actualTime = actualTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public long getActualTime() {
        return actualTime;
    }

    public void setActualTime(long actualTime) {
        this.actualTime = actualTime;
    }
}
