package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davids-iMac on 25/10/15.
 */
@Table(name = "DISHES")
public class CategoryDomain implements Serializable {

    @Column(name = "id")
    private long id;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private int priority;

    private List<DishDomain> dishes = new ArrayList<DishDomain>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<DishDomain> getDishes() {
        if (dishes == null) dishes = new ArrayList<DishDomain>();
        return dishes;
    }

    public void setDishes(List<DishDomain> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "CategoryDomain{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", position=" + priority +
                '}';
    }

    public Object toElasticSearchObject() {
        return new ElasticSearchCategory(this);
    }

    public static ElasticSearchCategory parse(String category, List<DishDomain> dishDomains) {
        ElasticSearchCategory elasticSearchCategory = new ElasticSearchCategory(category, dishDomains);
        return elasticSearchCategory;
    }

    public static class ElasticSearchCategory implements Serializable {
        public long id;
        public String name;
        public List<DishDomain.ElasticSearchDish> dishes;

        public ElasticSearchCategory(CategoryDomain category) {
            this.id = category.id;
            this.name = category.name;
            this.dishes = DishDomain.parse(category.getDishes());
        }

        public ElasticSearchCategory(String name, List<DishDomain> dishDomains) {
            this.name = name;
            this.dishes = DishDomain.parse(dishDomains);
        }
    }

    public static List<ElasticSearchCategory> parse(List<CategoryDomain> categories) {
        ArrayList<ElasticSearchCategory> list = new ArrayList<ElasticSearchCategory>();
        for (CategoryDomain category : categories) {
            list.add(new ElasticSearchCategory(category));
        }
        return list;
    }
}
