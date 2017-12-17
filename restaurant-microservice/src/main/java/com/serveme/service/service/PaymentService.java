package com.serveme.service.service;

import java.util.List;

import com.serveme.service.domain.RestaurantTab;;

public interface PaymentService {

    List<RestaurantTab> getRestaurantsTabs(long restaurantId);
}
