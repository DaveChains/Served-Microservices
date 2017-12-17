package com.serveme.service.domain;

import org.apache.commons.lang3.time.DateUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Davids-iMac on 25/10/15.
 */
@Table(name = "DISHES")
public class DishDomain implements Serializable {

    @Column(name = "id")
    private long id;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "position")
    private int priority;

    @Column(name = "runout")
    private Date runout;

    private boolean available;

    private List<FoodOptionDomain> options;

    private List<ExtraDomain> extras;

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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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

    public List<FoodOptionDomain> getOptions() {
        return options;
    }

    public void setOptions(List<FoodOptionDomain> options) {
        this.options = options;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "DishDomain{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    public Object toElasticSearchObject() {
        return new ElasticSearchDish(this);
    }

    public void setExtras(List<ExtraDomain> extras) {
        this.extras = extras;
    }

    public List<ExtraDomain> getExtras() {
        return extras;
    }

    public Date getRunout() {
        if (runout == null)
            runout = new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000));
        return runout;
    }

    public void setRunout(Date runout) {
        this.runout = runout;
    }

    public boolean isAvailable() {
        if (DateUtils.isSameDay(this.runout, new Date())) {
            return false;
        } else {
            return true;
        }
    }

    public static class ElasticSearchDish implements Serializable {
        public long id;
        public String name;
        public String description;
        public BigDecimal price;

        public ElasticSearchDish(DishDomain dish) {
            this.id = dish.id;
            this.name = dish.name;
            this.description = dish.description;
            this.price = dish.price;
        }
    }

    public static List<ElasticSearchDish> parse(List<DishDomain> dishes) {
        ArrayList<ElasticSearchDish> list = new ArrayList<ElasticSearchDish>();
        for (DishDomain dish : dishes) {
            list.add(new ElasticSearchDish(dish));
        }
        return list;
    }
}
