package com.serveme.service.dao;

import com.serveme.service.domain.ScheduleDomain;

import java.util.List;

/**
 * Created by Edgar on 1/24/2016.
 */
public interface ScheduleDao {

    int update(List<ScheduleDomain> scheduleDomainList, long restaurantId);

    List<ScheduleDomain> getByRestaurantId(long restaurantId);

    void delete(long id, long restaurantId);
}
