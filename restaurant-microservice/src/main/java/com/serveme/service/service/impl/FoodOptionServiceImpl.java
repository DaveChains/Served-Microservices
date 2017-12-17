package com.serveme.service.service.impl;

import com.serveme.service.dao.FoodOptionDao;
import com.serveme.service.domain.FoodOptionDomain;
import com.serveme.service.service.FoodOptionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Service
public class FoodOptionServiceImpl implements FoodOptionService {


    Logger logger = Logger.getLogger(FoodOptionServiceImpl.class.getName());

    @Inject
    protected FoodOptionDao foodOptionDao;

    @Override
    public long createFoodOption(FoodOptionDomain foodOptionDomain) {
        long foodOptionId = foodOptionDao.create(foodOptionDomain);
        foodOptionDomain.setId(foodOptionId);
        return foodOptionId;
    }

    @Override
    public long update(FoodOptionDomain foodOptionDomain) {
        return foodOptionDao.update(foodOptionDomain);
    }


    @Override
    public FoodOptionDomain getById(long id) {
        FoodOptionDomain foodOptionDomain = foodOptionDao.findById(id);
        return foodOptionDomain;
    }

    @Override
    public List<FoodOptionDomain> getByDish(long dishId) {
        return foodOptionDao.findByDish(dishId);
    }

    @Override
    public List<FoodOptionDomain> getByRestaurant(long restaurantId) {
        return foodOptionDao.findByRestaurant(restaurantId);
    }

    @Override
    public void remove(long id, long restaurantId) {
        //Todo add checks for same restaurant?
        foodOptionDao.removeById(id);
    }

    @Override
    public void removeAllFromDish(long dishId, long restaurantId) {
        //Todo add checks for same restaurant?
        foodOptionDao.removeAllByDish(dishId);
    }
}
