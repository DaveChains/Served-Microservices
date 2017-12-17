package com.serveme.service.service.impl;

import com.serveme.service.dao.CategoryDao;
import com.serveme.service.dao.DishDao;
import com.serveme.service.domain.CategoryDomain;
import com.serveme.service.domain.DishDomain;
import com.serveme.service.domain.ExtraDomain;
import com.serveme.service.domain.FoodOptionDomain;
import com.serveme.service.service.CategoryService;
import com.serveme.service.service.ExtrasService;
import com.serveme.service.service.FoodOptionService;
import com.serveme.service.service.SearchingEngineService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class CategoryServiceImpl implements CategoryService {


    Logger logger = Logger.getLogger(CategoryServiceImpl.class.getName());

    @Inject
    protected SearchingEngineService searchingEngineService;

    @Inject
    protected FoodOptionService foodOptionService;

    @Inject
    protected ExtrasService extrasService;

    @Inject
    protected CategoryDao categoryDao;

    @Inject
    protected DishDao dishDao;

    @Override
    public long createCategory(CategoryDomain category) {

        long categoryId = categoryDao.create(category);
        category.setId(categoryId);
        //searchingEngineService.sendCategory(category);
        return categoryId;
    }

    @Override
    public void update(CategoryDomain category) {
        categoryDao.update(category);
        //searchingEngineService.sendCategory(category);
    }

    @Override
    public CategoryDomain getById(long id) {
        CategoryDomain category = categoryDao.findById(id);
        List<DishDomain> dishList = dishDao.findByRestaurantAndCategory(category.getRestaurantId(), category.getId());
        setFoodOptions(category.getRestaurantId(), dishList);
        category.setDishes(dishList);
        return category;
    }

    private void setFoodOptions(long restaurantId, List<DishDomain> dishList) {
        List<FoodOptionDomain> foodOptions = foodOptionService.getByRestaurant(restaurantId);
        List<ExtraDomain> extras = extrasService.getByRestaurantId(restaurantId);

        Map<Long, DishDomain> dishes = new HashMap<Long, DishDomain>();

        for (DishDomain dish : dishList) {
            dish.setOptions(new ArrayList<>());
            dish.setExtras(new ArrayList<>());
            dishes.put(dish.getId(), dish);
        }

        for (FoodOptionDomain option : foodOptions) {
            if (dishes.get(option.getDishId()) != null && dishes.get(option.getDishId()).getOptions() != null)
                dishes.get(option.getDishId()).getOptions().add(option);
        }
        for (ExtraDomain extraDomain : extras) {
            if (dishes.get(extraDomain.getDishId()) != null && dishes.get(extraDomain.getDishId()).getExtras() != null)
                dishes.get(extraDomain.getDishId()).getExtras().add(extraDomain);
        }
    }

    @Override
    public List<CategoryDomain> getByRestaurant(long restaurantId) {
        return categoryDao.findByRestaurant(restaurantId);

    }

    @Override
    public void remove(long restaurantId, long categoryId) {
        categoryDao.removeById(restaurantId, categoryId);
        //searchingEngineService.deleteCategory(restaurantId, categoryId);
    }
}
