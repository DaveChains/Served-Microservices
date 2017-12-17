package com.serveme.service.dao;

import com.serveme.service.domain.CategoryDomain;

import java.util.List;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public interface CategoryDao {
    long create(CategoryDomain category);

    void update(CategoryDomain category);

    CategoryDomain findById(long id);

    List<CategoryDomain> findByRestaurant(long restaurantId);

    void removeById(long restaurantId, long id);
}
