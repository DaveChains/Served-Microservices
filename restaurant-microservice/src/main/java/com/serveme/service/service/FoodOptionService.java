package com.serveme.service.service;

import com.serveme.service.domain.FoodOptionDomain;

import java.util.List;

/**
 * Created by Edgar on 1/24/2016.
 */
public interface FoodOptionService {
    long createFoodOption(FoodOptionDomain foodOptionDomain);

    long update(FoodOptionDomain foodOptionDomain);

    FoodOptionDomain getById(long id);

    List<FoodOptionDomain> getByDish(long dishId);

    List<FoodOptionDomain> getByRestaurant(long restaurantId);

    void remove(long optionId, long restaurantId);

    void removeAllFromDish(long dishId, long restaurantId);
}
