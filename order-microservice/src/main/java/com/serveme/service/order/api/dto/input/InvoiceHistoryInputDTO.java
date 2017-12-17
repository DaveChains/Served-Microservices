package com.serveme.service.order.api.dto.input;

import java.io.Serializable;

public class InvoiceHistoryInputDTO implements Serializable {

    private int from;

    private int size;

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
}