package com.serveme.service.dao;

import com.serveme.service.domain.HolidayDomain;

import java.util.List;

/**
 * Created by Edgar on 3/28/2016.
 */
public interface HolidayDao {
    long create(HolidayDomain holiday);

    List<HolidayDomain> getByRestaurantId(long restaurantId);

    void removeById(long id, long restaurantId);

    long update(List<HolidayDomain> holidays, long restaurantId);
}
