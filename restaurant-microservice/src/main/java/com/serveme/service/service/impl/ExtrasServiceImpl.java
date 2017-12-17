package com.serveme.service.service.impl;

import com.serveme.service.dao.ExtrasDao;
import com.serveme.service.domain.ExtraDomain;
import com.serveme.service.service.ExtrasService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ExtrasServiceImpl implements ExtrasService {


    Logger logger = Logger.getLogger(ExtrasServiceImpl.class.getName());

    @Inject
    protected ExtrasDao extrasDao;

    @Override
    public int update(List<ExtraDomain> list, long restaurantId) {
        return extrasDao.update(list, restaurantId);
    }

    @Override
    public List<ExtraDomain> getByRestaurantId(long restaurantId, long dishId) {
        return extrasDao.findById(restaurantId, dishId);
    }

    @Override
    public List<ExtraDomain> getByRestaurantId(long restaurantId) {
        return extrasDao.findById(restaurantId);
    }

    @Override
    public void deleteById(long id, long restaurantId) {
        extrasDao.removeById(id, restaurantId);
    }
}
