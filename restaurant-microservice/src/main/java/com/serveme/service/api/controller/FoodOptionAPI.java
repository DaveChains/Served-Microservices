package com.serveme.service.api.controller;

import com.serveme.service.domain.FoodOptionDomain;
import com.serveme.service.service.FoodOptionService;
import com.serveme.service.util.BaseController;
import com.serveme.service.util.exception.http.BadRequestException;
import com.serveme.service.util.validation.SimpleValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class FoodOptionAPI extends BaseController {

    Logger logger = Logger.getLogger(FoodOptionAPI.class.getName());

    @Inject
    protected FoodOptionService foodOptionService;

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish/{dishId}/option", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Long> createFoodOption(@PathVariable("restaurantId") long restaurantId,
                                                 @PathVariable("dishId") long dishId,
                                                 @RequestBody FoodOptionDomain foodOption) {

        logger.log(Level.INFO, "POST : restaurant/option");
        foodOption.setRestaurantId(restaurantId);
        foodOption.setDishId(dishId);
        new SimpleValidator()
                .notNull("value", "restaurantId", "dishId")
                .graterThan("restaurantId", 0)
                .graterThan("dishId", 0)
                .validate(foodOption);
        return new ResponseEntity<Long>(foodOptionService.createFoodOption(foodOption), HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish/{dishId}/option", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateFoodOption(@PathVariable("restaurantId") long restaurantId,
                                           @PathVariable("dishId") long dishId,
                                           @RequestBody FoodOptionDomain foodOption) {

        logger.log(Level.INFO, "POST : restaurant/option");
        foodOption.setRestaurantId(restaurantId);
        foodOption.setDishId(dishId);
        new SimpleValidator()
                .notNull("value", "restaurantId", "dishId")
                .graterThan("restaurantId", 0)
                .graterThan("dishId", 0)
                .validate(foodOption);
        foodOptionService.update(foodOption);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish/{dishId}/option/{id}", method = RequestMethod.GET, produces = "application/json")
    public FoodOptionDomain getById(@PathVariable("restaurantId") long restaurantId,
                                    @PathVariable("dishId") long dishId,
                                    @PathVariable("id") long id) {

        logger.log(Level.INFO, "GET : restaurant/option/" + id);
        FoodOptionDomain foodOptionDomain = foodOptionService.getById(id);

        if (foodOptionDomain.getRestaurantId() != restaurantId || foodOptionDomain.getDishId() != dishId) {
            throw new BadRequestException("not access to this entity", "not access to this entity");
        }

        return foodOptionDomain;
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish/{dishId}/option/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable("restaurantId") long restaurantId,
                                 @PathVariable("dishId") long dishId,
                                 @PathVariable("id") long id) {

        logger.log(Level.INFO, "DELETE : /foodOption/" + id);
        foodOptionService.remove(id, restaurantId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
