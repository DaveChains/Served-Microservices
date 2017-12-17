package com.serveme.service.dao;

import com.serveme.service.domain.UserRestaurantTokenDomain;

/**
 * Created by Davids-iMac on 22/10/15.
 */
public interface UserRestaurantsTokenDao {

    void saveToken(UserRestaurantTokenDomain userRestaurantTokenDomain);


    void deleteTokensByRestaurant(long restaurantId);
}
