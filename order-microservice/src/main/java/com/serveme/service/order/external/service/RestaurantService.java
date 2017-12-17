package com.serveme.service.order.external.service;

import com.serveme.service.order.domain.DiscountDomain;
import com.serveme.service.order.domain.Location;
import com.serveme.service.order.domain.RestaurantDistanceData;
import com.serveme.service.order.domain.RestaurantOrder;
import com.serveme.service.order.external.dto.output.DevicesDTO;
import com.serveme.service.order.external.dto.output.RestaurantExDTO;
import com.serveme.service.order.external.dto.output.RestaurantsIdsDTO;
import com.serveme.service.order.external.dto.output.UserRestaurantExDTO;

import java.util.List;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public interface RestaurantService {

    RestaurantExDTO getRestaurant(long id);

    UserRestaurantExDTO authRestaurant(String accessToken);

    DevicesDTO getRestaurantDevice(long restaurantId);

    DevicesDTO getAdminDevices();

    RestaurantOrder getRestaurantData(long id);

    RestaurantsIdsDTO getAllRestaurants();

    boolean isRestaurantOpen(long restaurantId, long time);

    boolean isRestaurantOnline(long restaurantId);

    List<DiscountDomain> getDiscounts(long restaurantId);

    RestaurantDistanceData getValidDistance(long restaurantId, Location location);
}
