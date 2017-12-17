package com.serveme.service.order.api.dto.input;

import java.util.List;

/**
 * Created by Davids-iMac on 21/11/15.
 */

public class OrderedItemInputDTO {

    private int num;

    private long dishId;

    private String comments;

    private String option;

    private List<Long> extras;
    
    private boolean starter;

    public OrderedItemInputDTO(){}

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
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

    public List<Long> getExtras() {
        return extras;
    }

    public void setExtras(List<Long> extras) {
        this.extras = extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderedItemInputDTO)) return false;

        OrderedItemInputDTO that = (OrderedItemInputDTO) o;

        return dishId == that.dishId;

    }

    @Override
    public int hashCode() {
        return (int) (dishId ^ (dishId >>> 32));
    }


    @Override
    public String toString() {
        return "OrderedItemInputDTO{" +
                "num=" + num +
                ", dishId=" + dishId +
                ", option=" + option +
                ", comments='" + comments + '\'' +
                '}';
    }
}