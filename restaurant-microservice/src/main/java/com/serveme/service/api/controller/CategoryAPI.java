package com.serveme.service.api.controller;

import com.serveme.service.domain.CategoryDomain;
import com.serveme.service.service.CategoryService;
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
public class CategoryAPI extends BaseController {
    /**
     * TODO add secret key in internal services
     */

    Logger logger = Logger.getLogger(CategoryAPI.class.getName());

    @Inject
    protected CategoryService categoryService;

    @Inject
    protected RestaurantService restaurantService;


    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/category", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Long> createCategory(@PathVariable("restaurantId") long restaurantId, @RequestBody CategoryDomain category) {

        logger.log(Level.INFO, "POST : restaurant/category");
        category.setRestaurantId(restaurantId);
        new SimpleValidator()
                .notNull("name", "restaurantId")
                .graterThan("restaurantId", 0)
                .graterThan("priority", 0)
                .equal("restaurantId", restaurantId)
                .validate(category);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity<Long>(categoryService.createCategory(category), HttpStatus.CREATED);

    }


    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/category", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateCategory(@PathVariable("restaurantId") long restaurantId, @RequestBody CategoryDomain category) {

        logger.log(Level.INFO, "PUT : restaurant/dish/");
        new SimpleValidator()
                .notNull("id", "name", "restaurantId")
                .graterThan("restaurantId", 0)
                .graterThan("id", 0)
                .graterThan("priority", 0)
                .equal("restaurantId", restaurantId)
                .validate(category);
        categoryService.update(category);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);

    }


    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/category/{id}", method = RequestMethod.GET, produces = "application/json")
    public CategoryDomain getById(@PathVariable("restaurantId") long restaurantId, @PathVariable("id") long id) {

        logger.log(Level.INFO, "GET : restaurant/category/" + id);
        CategoryDomain categoryDomain = categoryService.getById(id);

        if (categoryDomain.getRestaurantId() != restaurantId) {
            throw new BadRequestException("not access to this entity", "not access to this entity");
        }

        return categoryDomain;

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/category", method = RequestMethod.GET, produces = "application/json")
    public List<CategoryDomain> getByRestaurant(@PathVariable("restaurantId") long restaurantId) {

        logger.log(Level.INFO, "GET : restaurant/" + restaurantId + "/category");
        return categoryService.getByRestaurant(restaurantId);

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/category/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable("restaurantId") long restaurantId, @PathVariable("id") long id) {

        logger.log(Level.INFO, "DELETE : /category/" + id);
        categoryService.remove(restaurantId, id);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);

    }

}
