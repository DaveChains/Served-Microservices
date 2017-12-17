package com.serveme.service.service;

import com.serveme.service.domain.CategoryDomain;

import java.util.List;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public interface CategoryService {

    long createCategory(CategoryDomain category);

    void update(CategoryDomain category);

    CategoryDomain getById(long id);

    List<CategoryDomain> getByRestaurant(long restaurantId);

    void remove(long restaurantId, long id);

}
