package com.serveme.service.service.impl;

import com.serveme.service.dao.ScheduleDao;
import com.serveme.service.domain.ScheduleDomain;
import com.serveme.service.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    Logger logger = Logger.getLogger(ScheduleServiceImpl.class.getName());

    @Inject
    protected ScheduleDao scheduleDao;

    @Override
    public long update(List<ScheduleDomain> scheduleDomainList, long restaurantId) {
        return scheduleDao.update(scheduleDomainList, restaurantId);
    }

    @Override
    public List<ScheduleDomain> getByRestaurantId(long restaurantId) {
        return scheduleDao.getByRestaurantId(restaurantId);
    }

    @Override
    public void deleteById(long id, long restaurantId) {
        scheduleDao.delete(id, restaurantId);
    }

}
