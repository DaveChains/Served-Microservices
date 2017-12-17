package com.serveme.service.order.domain;

/**
 * Created by Edgar on 3/13/2016.
 */
public class DayInfo {
    private String label;

    private int orders;

    private float sales;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public float getSales() {
        return sales;
    }

    public void setSales(float sales) {
        this.sales = sales;
    }
}
