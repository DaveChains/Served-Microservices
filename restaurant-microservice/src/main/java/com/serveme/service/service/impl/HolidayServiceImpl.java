package com.serveme.service.service.impl;

import com.serveme.service.dao.HolidayDao;
import com.serveme.service.domain.HolidayDomain;
import com.serveme.service.service.HolidayService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Service
public class HolidayServiceImpl implements HolidayService {

    Logger logger = Logger.getLogger(HolidayServiceImpl.class.getName());

    @Inject
    protected HolidayDao holidayDao;

    @Override
    public long create(HolidayDomain holiday) {
        return holidayDao.create(holiday);
    }

    @Override
    public long update(List<HolidayDomain> holidays, long restaurantId) {
        return holidayDao.update(holidays, restaurantId);
    }

    @Override
    public void removeById(long id, long restaurantId) {
        holidayDao.removeById(id, restaurantId);
    }

    @Override
    public List<HolidayDomain> getByRestaurantId(long restaurantId) {
        return holidayDao.getByRestaurantId(restaurantId);
    }
}
