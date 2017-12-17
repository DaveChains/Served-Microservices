package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Edgar on 3/26/2016.
 */
@Table(name = "SCHEDULES")
public class ScheduleDomain implements Serializable {
    @Column(name = "id")
    private long id;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "day")
    private int dayOfTheWeek;

    @Column(name = "init_hour")
    private String initHour;

    @Column(name = "end_hour")
    private String endHour;

    public ScheduleDomain() {
    }

    public ScheduleDomain(int dayOfTheWeek, String initHour, String endHour) {
        this.dayOfTheWeek = dayOfTheWeek;
        this.initHour = initHour;
        this.endHour = endHour;
    }

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

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
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

    @Override
    public String toString() {
        return "ScheduleDomain{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", dayOfTheWeek=" + dayOfTheWeek +
                ", initHour='" + initHour + '\'' +
                ", endHour='" + endHour + '\'' +
                '}';
    }
}
