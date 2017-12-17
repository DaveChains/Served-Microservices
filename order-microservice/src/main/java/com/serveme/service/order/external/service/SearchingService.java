package com.serveme.service.order.external.service;

public interface SearchingService {
    boolean isRestaurantOpen(long restaurantId, long time);
}
