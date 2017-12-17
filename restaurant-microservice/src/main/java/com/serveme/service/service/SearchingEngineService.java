package com.serveme.service.service;

import com.serveme.service.domain.*;

import java.util.List;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public interface SearchingEngineService {

    void sendRestaurant(RestaurantDomain restaurant);

    void sendCategory(CategoryDomain category);

    void deleteCategory(long restaurantId, long categoryId);

    void sendDish(DishDomain dish);

    void deleteDish(long restaurantId, long categoryId, long dishId);
    
    List<SearchingEngineRestaurantDomain> search(SearchObject search, String accessToken);
}
