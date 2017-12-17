package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "FOOD_OPTIONS")
public class FoodOptionDomain implements Serializable {

    @Column(name = "id")
    private long id;

    @Column(name = "dish_id")
    private long dishId;

    @Column(name = "value")
    private String value;

    @Column(name = "status")
    private long status;

    @Column(name = "restaurant_id")
    private long restaurantId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public FoodOptionDomain() {

    }

    public FoodOptionDomain(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "FoodOptionDomain{" +
                "id=" + id +
                ", dishId=" + dishId +
                ", value=" + value +
                ", status=" + status +
                '}';
    }
}
