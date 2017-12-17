package com.serveme.service.searching.domain;

import java.io.Serializable;

/**
 * Created by Edgar on 3/28/2016.
 */
public class HolidayDomain implements Serializable {
    private long id;

    private long restaurantId;

    private String initHour;

    private String endHour;

    private String date;

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

    public String getInitHour() {
        return initHour;
    }

    public void setInitHour(String initHour) {
        this.initHour = initHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
