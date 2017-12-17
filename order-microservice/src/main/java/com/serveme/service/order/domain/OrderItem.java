package com.serveme.service.order.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class OrderItem implements Serializable {

    private long dishId;

    private String dishName;

    private String dishDescription;

    private int num;

    private BigDecimal dishPrice;

    private String comments;

    private String option;
    
    private boolean starter;

    private List<ExtraDomain> extras;

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(BigDecimal dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isStarter() {
		return starter;
	}

	public void setStarter(boolean starter) {
		this.starter = starter;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) o;

        return dishId == orderItem.dishId;

    }

    @Override
    public int hashCode() {
        return (int) (dishId ^ (dishId >>> 32));
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "dishId=" + dishId +
                ", dishName='" + dishName + '\'' +
                ", dishDescription='" + dishDescription + '\'' +
                ", num=" + num +
                ", option=" + option +
                ", dishPrice=" + dishPrice +
                ", comments='" + comments + '\'' +
                '}';
    }

    public List<ExtraDomain> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtraDomain> extras) {
        this.extras = extras;
    }
}
