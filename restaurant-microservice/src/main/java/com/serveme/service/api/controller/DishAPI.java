package com.serveme.service.api.controller;

import com.serveme.service.domain.DishDomain;
import com.serveme.service.domain.FoodOptionDomain;
import com.serveme.service.service.DishService;
import com.serveme.service.service.ExtrasService;
import com.serveme.service.service.FoodOptionService;
import com.serveme.service.service.RestaurantService;
import com.serveme.service.util.BaseController;
import com.serveme.service.util.exception.http.BadRequestException;
import com.serveme.service.util.validation.SimpleValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Controller
public class DishAPI extends BaseController {


    Logger logger = Logger.getLogger(DishAPI.class.getName());

    @Inject
    protected DishService dishService;

    @Inject
    protected FoodOptionService foodOptionService;

    @Inject
    protected ExtrasService extrasService;

    @Inject
    protected RestaurantService restaurantService;

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Long> createDish(@PathVariable("restaurantId") long restaurantId, @RequestBody DishDomain dish) {

        logger.log(Level.INFO, "POST : restaurant/dish");
        dish.setRestaurantId(restaurantId);
        new SimpleValidator()
                .notNull("name", "categoryId", "restaurantId", "price")
                .graterThan("categoryId", 0)
                .graterThan("restaurantId", 0)
                .graterThan("priority", 0)
                .graterThan("price", 0)
                .equal("restaurantId", restaurantId)
                .validate(dish);

        long dishId = dishService.createDish(dish);
        dish.setId(dishId);
        updateFoodOptions(dish);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity<Long>(dishId, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateDish(@PathVariable("restaurantId") long restaurantId, @RequestBody DishDomain dish) {

        logger.log(Level.INFO, "PUT : restaurant/dish/");
        new SimpleValidator()
                .notNull("id", "name", "categoryId", "restaurantId", "price")
                .graterThan("id", 0)
                .graterThan("categoryId", 0)
                .graterThan("restaurantId", 0)
                .graterThan("priority", 0)
                .graterThan("price", 0)
                .equal("restaurantId", restaurantId)
                .validate(dish);

        dishService.update(dish);
        updateFoodOptions(dish);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);
    }


    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish/{id}", method = RequestMethod.GET, produces = "application/json")
    public DishDomain getById(@PathVariable("restaurantId") long restaurantId, @PathVariable("id") long id) {

        logger.log(Level.INFO, "GET : restaurant/dish/" + id);
        DishDomain dishDomain = dishService.getById(id);
        if (dishDomain.getRestaurantId() != restaurantId) {
            throw new BadRequestException("not access to this entity", "not access to this entity");
        }
        dishDomain.setExtras(extrasService.getByRestaurantId(restaurantId, id));
        return dishDomain;

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/category/{categoryId}/dish", method = RequestMethod.GET, produces = "application/json")
    public List<DishDomain> getByCategory(@PathVariable("restaurantId") long restaurantId, @PathVariable("categoryId") long categoryId) {

        logger.log(Level.INFO, "GET : restaurant/" + restaurantId + "/category/" + categoryId + "/dish");
        return dishService.getByCategory(restaurantId, categoryId);

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/category/{categoryId}/dish/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable("restaurantId") long restaurantId, @PathVariable("categoryId") long categoryId, @PathVariable("id") long id) {

        logger.log(Level.INFO, "GET : restaurant/dish/" + id);
        dishService.remove(restaurantId, categoryId, id);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);

    }

    private void updateFoodOptions(DishDomain dish) {
        //Todo change logic when control in web panel is replaced
        if (dish.getOptions() != null) {
            foodOptionService.removeAllFromDish(dish.getId(), dish.getRestaurantId());
            for (FoodOptionDomain option : dish.getOptions()) {
                if (option.getId() == 0) {
                    option.setDishId(dish.getId());
                    option.setRestaurantId(dish.getRestaurantId());
                    foodOptionService.createFoodOption(option);
                } else {
                    foodOptionService.update(option);
                }
            }
        }
    }
}
