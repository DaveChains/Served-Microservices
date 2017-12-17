package com.serveme.service.order.api.dto.input;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 29/11/15.
 */
public class OrderHistoryInputDTO implements Serializable {

    private int from;

    private int size;

    private long startDate;

    private long endDate;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}