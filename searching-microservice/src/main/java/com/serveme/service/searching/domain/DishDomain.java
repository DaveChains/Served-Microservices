package com.serveme.service.searching.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public class DishDomain implements Serializable {

    private long id;

    private String name;

    private String description;

    private BigDecimal price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishDomain)) return false;

        DishDomain that = (DishDomain) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
