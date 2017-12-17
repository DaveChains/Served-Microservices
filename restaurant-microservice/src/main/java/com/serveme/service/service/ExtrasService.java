package com.serveme.service.service;

import com.serveme.service.domain.ExtraDomain;

import java.util.List;

/**
 * Created by Edgar on 1/24/2016.
 */
public interface ExtrasService {
    int update(List<ExtraDomain> list, long restaurantId);

    List<ExtraDomain> getByRestaurantId(long restaurantId, long dishId);

    List<ExtraDomain> getByRestaurantId(long restaurantId);

    void deleteById(long id, long restaurantId);
}
