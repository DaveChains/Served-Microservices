package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Edgar on 3/28/2016.
 */
@Table(name = "HOLIDAYS")
public class HolidayDomain implements Serializable {

    @Column(name = "id")
    private long id;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "init_hour")
    private Timestamp initHour;

    @Column(name = "end_hour")
    private Timestamp endHour;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getEndHour() {
        return endHour;
    }

    public void setEndHour(Timestamp endHour) {
        this.endHour = endHour;
    }

    public Timestamp getInitHour() {
        return initHour;
    }

    public void setInitHour(Timestamp initHour) {
        this.initHour = initHour;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
