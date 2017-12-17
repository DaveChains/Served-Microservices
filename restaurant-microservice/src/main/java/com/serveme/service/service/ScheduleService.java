package com.serveme.service.service;

import com.serveme.service.domain.ScheduleDomain;

import java.util.List;

public interface ScheduleService {

    long update(List<ScheduleDomain> scheduleDomainList, long restaurantId);

    List<ScheduleDomain> getByRestaurantId(long restaurantId);

    void deleteById(long id, long restaurantId);
}
