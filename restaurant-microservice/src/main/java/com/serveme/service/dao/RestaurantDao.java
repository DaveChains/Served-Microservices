package com.serveme.service.dao;

import com.serveme.service.api.dto.out.SearchingRestaurantDto;
import com.serveme.service.domain.CategoryDomain;
import com.serveme.service.domain.DishDomain;
import com.serveme.service.domain.RestaurantDomain;
import com.serveme.service.domain.SearchObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public interface RestaurantDao {

    long createBasic(String name);

    void update(RestaurantDomain restaurant);

    Map<String, List<DishDomain>> findMenuByRestaurant(long id);

    List<CategoryDomain> getCategoriesByRestaurant(long restaurantId);

    List<DishDomain> getDishesByRestaurant(long restaurantId);

    RestaurantDomain findById(long id);

    void removeById(long id);

    List<RestaurantDomain> findAllRestaurants();

    void setOnline(long id, boolean online);

    List<Long> getDisabledRestaurants();

    boolean isOnline(long restaurantId);

    List<SearchingRestaurantDto> search(SearchObject searchObject, boolean testMode, double maxDistance);
}
