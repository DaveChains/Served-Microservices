package com.serveme.payment.api.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderItemDto implements Serializable {

    private long dishId;

    private String dishName;

    private String dishDescription;

    private int num;

    private BigDecimal dishPrice;

    private String comments;

    private String option;
    
    private boolean starter;

    private List<ExtraDto> extras;


    public OrderItemDto() {}


    public OrderItemDto(
            long dishId,
            String dishName,
            BigDecimal dishPrice,
            int num) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.num = num;
    }

    public String getComments() {
        return comments;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public long getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public List<ExtraDto> getExtras() {
        return extras;
    }

    public int getNum() {
        return num;
    }

    public String getOption() {
        return option;
    }

    public boolean isStarter() {
        return starter;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "comments='" + comments + '\'' +
                ", dishId=" + dishId +
                ", dishName='" + dishName + '\'' +
                ", dishDescription='" + dishDescription + '\'' +
                ", num=" + num +
                ", dishPrice=" + dishPrice +
                ", option='" + option + '\'' +
                ", starter=" + starter +
                ", extras=" + extras +
                '}';
    }
}
