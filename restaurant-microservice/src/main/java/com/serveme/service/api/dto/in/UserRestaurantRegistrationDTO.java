package com.serveme.service.api.dto.in;

import com.serveme.service.domain.UserRestaurantDomain;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public class UserRestaurantRegistrationDTO implements Serializable{

    private String restaurantName;

    private UserRestaurantDomain user;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public UserRestaurantDomain getUser() {
        return user;
    }

    public void setUser(UserRestaurantDomain user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserRestaurantRegistrationDTO{" +
                "restaurantName='" + restaurantName + '\'' +
                ", user=" + user +
                '}';
    }
}
