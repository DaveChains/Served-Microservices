package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Edgar on 5/1/2016.
 */
@Table(name = "EXTRAS")
public class ExtraDomain implements Serializable {
    @Column(name = "id")
    private long id;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "dish_id")
    private long dishId;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "value")
    private String value;

    @Column(name = "price")
    private BigDecimal price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
