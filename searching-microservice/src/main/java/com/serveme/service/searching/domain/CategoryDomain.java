package com.serveme.service.searching.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public class CategoryDomain implements Serializable {

    private long id;

    private String name;

    private List<DishDomain> dishes;

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

    public List<DishDomain> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishDomain> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDomain)) return false;

        CategoryDomain that = (CategoryDomain) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
