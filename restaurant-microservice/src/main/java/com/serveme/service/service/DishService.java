package com.serveme.service.service;

import com.serveme.service.domain.DishDomain;

import java.util.List;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public interface DishService {

    long createDish(DishDomain dish);

    void update(DishDomain dishDomain);

    DishDomain getById(long id);

    List<DishDomain> getByCategory(long restaurantId, long categoryId);

    void remove(long restaurantId, long categoryId, long id);
    
    void setRunoutForToday(long dishId, boolean isRunout);
}
