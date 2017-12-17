package com.serveme.service.dao;

import com.serveme.service.domain.DiscountDomain;
import com.serveme.service.domain.ScheduleDomain;

import java.util.List;

/**
 * Created by Edgar on 1/24/2016.
 */
public interface DiscountDao {

    int update(List<DiscountDomain> list, long restaurantId);

    List<DiscountDomain> getByRestaurantId(long restaurantId);

    void delete(long id, long restaurantId);
}
