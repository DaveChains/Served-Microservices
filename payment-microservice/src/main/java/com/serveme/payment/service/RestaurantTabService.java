package com.serveme.payment.service;

import com.serveme.payment.domain.RestaurantTab;

import java.time.LocalDate;

public interface RestaurantTabService {
    RestaurantTab getCurrentOrStartNew(long restaurantId);

    RestaurantTab getCurrentOpen(long restaurantId);

    RestaurantTab startTab(long restaurantId);

    LocalDate getCurrentPeriodInclusive();

    LocalDate getStartPeriodInclusiveFromDate(LocalDate targetDate);
}
