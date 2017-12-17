package com.serveme.service.service;

import com.serveme.service.api.dto.out.RestaurantsIdsDTO;
import com.serveme.service.api.dto.out.SearchingRestaurantDto;
import com.serveme.service.domain.CategoryDomain;
import com.serveme.service.domain.DishDomain;
import com.serveme.service.domain.RestaurantDomain;
import com.serveme.service.domain.SearchObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Davids-iMac on 06/11/15.
 */
public interface RestaurantService {

    long createBasic(String name);

    void update(RestaurantDomain restaurant);

    RestaurantDomain getById(long id);

    Map<String, List<DishDomain>> getMenuByRestaurant(long restaurantId);

    List<CategoryDomain> getCategoriesByRestaurant(long restaurantId);

    List<DishDomain> getDishesByRestaurant(long restaurantId);

    void sendToSearchingEngine(RestaurantDomain restaurant);

    void sendToSearchingEngine(long restaurantId);

    RestaurantsIdsDTO getAllRestaurantIds();

    List<RestaurantDomain> getAllRestaurants();

    void setOnline(long id, boolean online);

    List<Long> getDisabledRestaurants();

    boolean isOnline(long restaurantId);

    List<SearchingRestaurantDto> search(SearchObject searchObject, boolean showTestRestaurants);

    @Deprecated
    List<SearchingRestaurantDto> legacySearch(SearchObject searchObject, boolean showTestRestaurants);
}
