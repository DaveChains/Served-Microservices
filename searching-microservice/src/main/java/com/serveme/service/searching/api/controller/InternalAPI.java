package com.serveme.service.searching.api.controller;

import com.serveme.service.searching.domain.*;
import com.serveme.service.searching.exception.CategoryNotFoundException;
import com.serveme.service.searching.exception.RestaurantNotFoundException;
import com.serveme.service.searching.service.RestaurantService;
import com.serveme.service.searching.service.UserService;
import com.serveme.service.searching.util.BaseController;
import com.serveme.service.searching.util.exception.BadRequestException;
import com.serveme.service.searching.util.exception.NotFoundException;
import com.serveme.service.searching.util.validation.SimpleValidator;
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
public class InternalAPI extends BaseController {
    /**
     * TODO add secret key in internal services
     */

    Logger logger = Logger.getLogger(InternalAPI.class.getName());

    @Inject
    protected RestaurantService restaurantService;

    @Inject
    protected UserService userService;

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/{id}", method = RequestMethod.GET, produces = "application/json")
    public RestaurantDomain getRestaurant(@PathVariable("id") long id) {

        logger.log(Level.INFO, "GET : /internal/restaurant/" + id);
        if (id <= 0)
            throw new BadRequestException("Wrong id " + id, "Wrong id " + id);

        RestaurantDomain restaurantDomain = restaurantService.getRestaurant(id);

        if (restaurantDomain == null)
            throw new NotFoundException("Not found restaurant with id " + id, null);
        return restaurantDomain;
    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity putRestaurant(@RequestBody RestaurantDomain restaurant) {

        logger.log(Level.INFO, "PUT : /internal/restaurant");
        new SimpleValidator()
                .notNull("name", "id")
                .graterThan("id", 0)
                .validate(restaurant);

        boolean created = restaurantService.putRestaurant(restaurant);
        return new ResponseEntity(created ? HttpStatus.CREATED : HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/{restaurantId}/category", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity putCategory(@PathVariable("restaurantId") long restaurantId, @RequestBody CategoryDomain category) {

        logger.log(Level.INFO, "PUT : /internal/restaurant/" + restaurantId + "/category");
        new SimpleValidator()
                .notNull("name", "id")
                .graterThan("id", 0)
                .validate(category);

        boolean created = restaurantService.putCategory(restaurantId, category);
        return new ResponseEntity(created ? HttpStatus.CREATED : HttpStatus.OK);


    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/{restaurantId}/category/{categoryId}/dish", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity putDish(@PathVariable("restaurantId") long restaurantId, @PathVariable("categoryId") long categoryId, @RequestBody DishDomain dish) {

        logger.log(Level.INFO, "PUT : /internal/restaurant/" + restaurantId + "/category/" + categoryId + "/dish");
        new SimpleValidator()
                .notNull("id", "name", "price")
                .graterThan("id", 0)
                .validate(dish);

        boolean created = restaurantService.putDish(restaurantId, categoryId, dish);
        return new ResponseEntity(created ? HttpStatus.CREATED : HttpStatus.OK);


    }

    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/_search", method = RequestMethod.POST, produces = "application/json")
    public List<RestaurantDomain> findRestaurantsLegacy(@RequestBody SearchObject searchObject) {

        logger.log(Level.INFO, "POST : /searching/restaurant/_search");
        new SimpleValidator()
                .notNull("location", "termsIn")
                .validate(searchObject);
        searchObject.setShowTestRestaurants(true);
        return restaurantService.findRestaurants(searchObject);
    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/search", method = RequestMethod.POST, produces = "application/json")
    public List<RestaurantDomain> findRestaurants(@RequestHeader("access-token") String accessToken,
                                                  @RequestBody SearchObject searchObject) {

        logger.log(Level.INFO, "POST : /searching/restaurant/search");
        new SimpleValidator()
                .notNull("location", "termsIn")
                .validate(searchObject);

        logger.log(Level.INFO, "POST : Requesting user type");
        UserExDTO user = userService.getUser(accessToken);

        searchObject.setShowTestRestaurants(user.isTestUser());
        return restaurantService.findRestaurants(searchObject);
    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/{restaurantId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity inactiveRestaurant(@PathVariable("restaurantId") long restaurantId) {

        logger.log(Level.INFO, "DELETE : /internal/restaurant/" + restaurantId);

        boolean deleted = restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/{restaurantId}/category/{categoryId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteCategory(@PathVariable("restaurantId") long restaurantId, @PathVariable("categoryId") long categoryId) {

        logger.log(Level.INFO, "DELETE : /internal/restaurant/" + restaurantId + "/category/" + categoryId + "/dish");

        boolean deleted = restaurantService.deleteCategory(restaurantId, categoryId);
        return new ResponseEntity(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/{restaurantId}/category/{categoryId}/dish/{dishId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteDish(@PathVariable("restaurantId") long restaurantId, @PathVariable("categoryId") long categoryId, @PathVariable("dishId") long dishId) {

        logger.log(Level.INFO, "DELETE : /internal/restaurant/" + restaurantId + "/category/" + categoryId + "/dish/" + dishId);

        boolean deleted = restaurantService.deleteDish(restaurantId, categoryId, dishId);
        return new ResponseEntity(deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @RequestMapping(value = "/internal/searching/restaurant/{restaurantId}/isopen/{timestamp}", method = RequestMethod.GET, produces = "application/json")
    public Boolean isRestaurantOpen(@PathVariable("restaurantId") long restaurantId, @PathVariable("timestamp") long time) {

        logger.log(Level.INFO, "POST : /internal/searching/restaurant/isopen");
        if (restaurantId <= 0)
            throw new BadRequestException("Wrong id " + restaurantId, "Wrong id " + restaurantId);

        RestaurantDomain restaurantDomain = restaurantService.getRestaurant(restaurantId);

        if (restaurantDomain == null)
            throw new NotFoundException("Not found restaurant with id " + restaurantId, null);

        restaurantDomain.determinateOpenState(time);

        return restaurantDomain.isOpen();
    }

    //EXCEPTION HANDLER
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity("{\"restaurantId\":" + ex.getRestaurantId() + "}", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> CategoryNotFoundException(CategoryNotFoundException ex) {
        logger.log(Level.SEVERE, ex.getMessage());
        return new ResponseEntity("{\"restaurantId\":" + ex.getRestaurantId() + ",\"categoryId\":" + ex.getCategoryId() + "}", HttpStatus.NOT_FOUND);
    }
}
