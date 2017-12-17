package com.serveme.service.dao;

import com.serveme.service.domain.DishDomain;

import java.util.List;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public interface DishDao {
    long create(DishDomain dish);

    void update(DishDomain dish);

    DishDomain findById(long id);

    List<DishDomain> findByRestaurantAndCategory(long restaurantId, long categoryId);

    void removeById(long restaurantId, long id);
}
