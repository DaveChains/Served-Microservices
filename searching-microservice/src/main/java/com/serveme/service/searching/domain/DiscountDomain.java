package com.serveme.service.searching.domain;

import java.io.Serializable;

/**
 * Created by Edgar on 4/23/2016.
 */
public class DiscountDomain implements Serializable {
    private long id;

    private long restaurantId;

    /**
     * Indicates from which order this discount will apply
     */
    private int initOrder;

    /**
     * Indicates the last order this discount will apply
     */
    private int endOrder;

    private long percentage;

    private long flatAmount;

    private String initDate;

    private String endDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getInitOrder() {
        return initOrder;
    }

    public void setInitOrder(int initOrder) {
        this.initOrder = initOrder;
    }

    public int getEndOrder() {
        return endOrder;
    }

    public void setEndOrder(int endOrder) {
        this.endOrder = endOrder;
    }

    public long getPercentage() {
        return percentage;
    }

    public void setPercentage(long percentage) {
        this.percentage = percentage;
    }

    public long getFlatAmount() {
        return flatAmount;
    }

    public void setFlatAmount(long flatAmount) {
        this.flatAmount = flatAmount;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
