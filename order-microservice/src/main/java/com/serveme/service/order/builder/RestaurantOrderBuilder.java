package com.serveme.service.order.builder;

import com.serveme.service.order.domain.RestaurantOrder;
import com.serveme.service.order.external.dto.output.RestaurantExDTO;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class RestaurantOrderBuilder {

    private RestaurantExDTO restaurant;


    public RestaurantOrderBuilder setRestaurant(RestaurantExDTO restaurant){
        this.restaurant = restaurant;
        return this;
    }


    public RestaurantOrder build(){
        RestaurantOrder restaurantOrder = new RestaurantOrder();
        restaurantOrder.setId(restaurant.getId());
        restaurantOrder.setName(restaurant.getName());
        restaurantOrder.setDescription(restaurant.getDescription());
        restaurantOrder.setAddress(restaurant.getAddress());
        restaurantOrder.setPostcode(restaurant.getPostcode());
        restaurantOrder.setCity(restaurant.getCity());
        restaurantOrder.setCountry(restaurant.getCountry());
        restaurantOrder.setLocationLat(restaurant.getLocationLat());
        restaurantOrder.setLocationLon(restaurant.getLocationLon());
        restaurantOrder.setEmailForClients(restaurant.getEmailForClients());
        restaurantOrder.setPhoneForClients(restaurant.getPhoneForClients());
        restaurantOrder.setPhotoUrl(restaurant.getPhotoUrl());
        return restaurantOrder;
    }

}
