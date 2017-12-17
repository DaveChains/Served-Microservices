package com.serveme.service.notification.external.dto.output;

/**
 * Created by DavidChains on 22/10/15.
 */
public class UserRestaurantExDTO {

    private long id;

    private long restaurantId;

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

    @Override
    public String toString() {
        return "UserRestaurantDomain{" +
                "id=" + id +
                ", restaurantId='" + restaurantId + "}";

    }
}
