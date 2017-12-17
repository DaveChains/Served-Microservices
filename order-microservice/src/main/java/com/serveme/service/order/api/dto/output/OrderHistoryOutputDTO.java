package com.serveme.service.order.api.dto.output;

import com.serveme.service.order.domain.Order;

import java.util.List;

/**
 * Created by Davids-iMac on 29/11/15.
 */
public class OrderHistoryOutputDTO {

    private int totalCount;

    private int maxPageSize;

    private int from;

    private int size;

    private List<Order> orders;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getMaxPageSize() {
        return maxPageSize;
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderHistoryOutputDTO{" +
                "totalCount=" + totalCount +
                ", maxPageSize=" + maxPageSize +
                ", from=" + from +
                ", size=" + size +
                ", orders=" + orders +
                '}';
    }
}
