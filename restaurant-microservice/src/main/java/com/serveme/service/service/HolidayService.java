package com.serveme.service.service;

import com.serveme.service.domain.HolidayDomain;

import java.util.List;

/**
 * Created by Edgar on 3/28/2016.
 */
public interface HolidayService {
    long create(HolidayDomain holiday);

    long update(List<HolidayDomain> holidays, long restaurantId);

    void removeById(long id, long restaurantId);

    List<HolidayDomain> getByRestaurantId(long restaurantId);
}
