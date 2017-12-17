package com.serveme.service.dao;

import com.serveme.service.domain.UserRestaurantDomain;

import java.util.List;

/**
 * Created by Davids-iMac on 22/10/15.
 */
public interface UserRestaurantDao {
    long create(UserRestaurantDomain userRestaurantDomain);

    void update(UserRestaurantDomain user);

    void invalidateSessionDeviceByRestaurant(long restaurantId);

    UserRestaurantDomain findById(long id);

    UserRestaurantDomain findByRestaurantId(long id);

    List<UserRestaurantDomain> findAdminUsers();

    void removeById(long id);

    UserRestaurantDomain findByEmail(String email);

    UserRestaurantDomain findByAccessToken(String accessToken);
}
