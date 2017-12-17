package com.serveme.service.dao;

import com.serveme.service.domain.ExtraDomain;

import java.util.List;

/**
 * Created by Edgar on 5/1/2016.
 */
public interface ExtrasDao {
    int update(List<ExtraDomain> list, long restaurantId);

    List<ExtraDomain> findById(long restaurantId, long dishId);

    void removeById(long id, long restaurantId);

    List<ExtraDomain> findById(long restaurantId);
}
