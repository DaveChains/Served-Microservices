package com.serveme.service.service.impl;

import com.serveme.service.dao.DishDao;
import com.serveme.service.dao.FoodOptionDao;
import com.serveme.service.domain.DishDomain;
import com.serveme.service.domain.FoodOptionDomain;
import com.serveme.service.service.DishService;
import com.serveme.service.service.SearchingEngineService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.util.Date;
import java.util.List;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class DishServiceImpl implements DishService {

    @Inject
    protected SearchingEngineService searchingEngineService;

    @Inject
    protected DishDao dishDao;

    @Inject
    protected FoodOptionDao foodOptionDao;

    public void setRunoutForToday(long dishId, boolean available) {
    	Date today = new Date();
    	DishDomain dish = dishDao.findById(dishId);
    	if (!available) {
    		dish.setRunout(today);
    	} else {
    		dish.setRunout(null);
    	}
    	dishDao.update(dish);
    }
    
    @Override
    public long createDish(DishDomain dish) {
        long id = dishDao.create(dish);
        dish.setId(id);
        //searchingEngineService.sendDish(dish);
        return id;
    }

    @Override
    public void update(DishDomain dishDomain) {
        dishDao.update(dishDomain);
        //searchingEngineService.sendDish(dishDomain);
    }


    @Override
    public DishDomain getById(long id) {
        DishDomain dish = dishDao.findById(id);
        List<FoodOptionDomain> foodOptionList = foodOptionDao.findByDish(dish.getId());
        dish.setOptions(foodOptionList);
        return dish;
    }

    @Override
    public List<DishDomain> getByCategory(long restaurantId, long categoryId) {
        return dishDao.findByRestaurantAndCategory(restaurantId, categoryId);

    }

    @Override
    public void remove(long restaurantId, long categoryId, long dishId) {
        dishDao.removeById(restaurantId, dishId);
        //searchingEngineService.deleteDish(restaurantId, categoryId, dishId);
    }
}
