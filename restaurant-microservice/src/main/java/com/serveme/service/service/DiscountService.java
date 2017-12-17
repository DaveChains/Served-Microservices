package com.serveme.service.service;

import com.serveme.service.domain.DiscountDomain;

import java.util.List;

public interface DiscountService {

    long update(List<DiscountDomain> list, long restaurantId);

    List<DiscountDomain> getByRestaurantId(long restaurantId);

    void deleteById(long id, long restaurantId);
}
