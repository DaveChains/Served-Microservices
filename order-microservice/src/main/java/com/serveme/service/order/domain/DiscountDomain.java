package com.serveme.service.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Created by Edgar on 5/5/2016.
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
    
    private int daysCount;

    public static BigDecimal calcDiscountPercentage(List<Long> finishedOrders, List<DiscountDomain> discounts) {
        if (finishedOrders == null || discounts == null) return new BigDecimal(0.0);

        Collections.sort(discounts, (lhs, rhs) ->
                lhs.getPercentage() > rhs.getPercentage() ? -1
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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

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

	public int getDaysCount() {
		return daysCount;
	}

	public void setDaysCount(int daysCount) {
		this.daysCount = daysCount;
	}
}
