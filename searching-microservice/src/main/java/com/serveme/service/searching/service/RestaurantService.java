package com.serveme.service.searching.service;

import com.serveme.service.searching.domain.CategoryDomain;
import com.serveme.service.searching.domain.DishDomain;
import com.serveme.service.searching.domain.RestaurantDomain;
import com.serveme.service.searching.domain.SearchObject;

import java.util.List;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public interface RestaurantService {

    RestaurantDomain getRestaurant(long id);

    boolean putRestaurant(RestaurantDomain restaurantDomain);

    boolean putCategory(long restaurantId, CategoryDomain category);

    boolean putDish(long restaurantId, long categoryId, DishDomain dish);

    boolean deleteRestaurant(long restaurantId);

    boolean deleteCategory(long restaurantId, long categoryId);

    boolean deleteDish(long restaurantId, long categoryId, long dishId);

    List<RestaurantDomain> findRestaurants(SearchObject searchObject);
}
