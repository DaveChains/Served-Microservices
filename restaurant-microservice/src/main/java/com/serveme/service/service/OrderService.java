package com.serveme.service.service;

import java.util.List;

/**
 * Created by DavidChains on 24/04/2016.
 */
public interface OrderService {

    List<List<Long>> countOrdersByUser(long userId, List<Long> restaurantIds);

    @SuppressWarnings("unchecked")
    String getTest();
}
