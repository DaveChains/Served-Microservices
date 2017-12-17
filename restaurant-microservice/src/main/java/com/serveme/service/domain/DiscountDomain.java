package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Edgar on 4/22/2016.
 */
@Table(name = "DISCOUNTS")
public class DiscountDomain implements Serializable {

    @Column(name = "id")
    private long id;

    @Column(name = "restaurant_id")
    private long restaurantId;

    /**
     * Indicates from which order this discount will apply
     */
    @Column(name = "init_order")
    private int initOrder;

    /**
     * Indicates the last order this discount will apply
     */
    @Column(name = "end_order")
    private int endOrder;

    @Column(name = "percentage")
    private long percentage;

    @Column(name = "flat_amount")
    private long flatAmount;

    @Column(name = "init_date")
    private String initDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "days_count")
    private int daysCount;

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

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public static BigDecimal calcDiscountPercentage(List<Long> finishedOrders, List<DiscountDomain> discounts) {
        if (finishedOrders == null || discounts == null) return new BigDecimal(0.0);

        Collections.sort(discounts, (lhs, rhs) ->
                lhs.getPercentage() > rhs.getPercentage()
                        ? -1
                        : (lhs.getPercentage() > rhs.getPercentage()) ? 1 : 0);

        for (DiscountDomain discount : discounts) {
            int orderCount = finishedOrders.size();
            if (discount.getDaysCount() > 0) {
                for (long daysSince : finishedOrders) {
                    if (daysSince > discount.getDaysCount()) {
                        orderCount--;
                    }
                }
            }
            if (orderCount + 1 >= discount.getInitOrder()
                    && orderCount + 1 <= discount.getEndOrder()) {
                return new BigDecimal(discount.getPercentage() / 100.00);
            }
        }
        return new BigDecimal(0.0);
    }
}
