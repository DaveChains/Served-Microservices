package com.serveme.service.dao;

import com.serveme.service.domain.FoodOptionDomain;

import java.util.List;

/**
 * Created by Edgar on 1/24/2016.
 */
public interface FoodOptionDao {

    long create(FoodOptionDomain foodOptionDomain);

    long update(FoodOptionDomain foodOptionDomain);

    FoodOptionDomain findById(long id);

    List<FoodOptionDomain> findByDish(long dishId);

    List<FoodOptionDomain> findByRestaurant(long restaurantId);

    void removeById(long id);

    void removeAllByDish(long dishId);
}
