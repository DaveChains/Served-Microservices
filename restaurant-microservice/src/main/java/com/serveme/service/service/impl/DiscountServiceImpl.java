package com.serveme.service.service.impl;

import com.serveme.service.dao.DiscountDao;
import com.serveme.service.domain.DiscountDomain;
import com.serveme.service.service.DiscountService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Service
public class DiscountServiceImpl implements DiscountService {

    Logger logger = Logger.getLogger(DiscountServiceImpl.class.getName());

    @Inject
    protected DiscountDao discountDao;

    @Override
    public long update(List<DiscountDomain> list, long restaurantId) {
        return discountDao.update(list, restaurantId);
    }

    @Override
    public List<DiscountDomain> getByRestaurantId(long restaurantId) {
        return discountDao.getByRestaurantId(restaurantId);
    }

    @Override
    public void deleteById(long id, long restaurantId) {
        discountDao.delete(id, restaurantId);
    }

}
