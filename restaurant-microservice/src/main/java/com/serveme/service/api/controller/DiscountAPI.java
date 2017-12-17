package com.serveme.service.api.controller;

import com.serveme.service.domain.DiscountDomain;
import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.service.DiscountService;
import com.serveme.service.service.RestaurantService;
import com.serveme.service.util.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Controller
public class DiscountAPI extends BaseController {

    Logger logger = Logger.getLogger(DiscountAPI.class.getName());

    @Inject
    protected DiscountService discountService;

    @Inject
    protected RestaurantService restaurantService;

    @Inject
    protected UserRestaurantAPI restaurantAPI;

    @ResponseBody
    @RequestMapping(value = "/restaurant/discount", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateDiscounts(@RequestHeader("access-token") String restaurantAccessToken,
                                          @RequestBody List<DiscountDomain> discounts) {
        UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
        logger.log(Level.INFO, "PUT : restaurant/discount/" + restaurantUser.getRestaurantId() + "/schedule");
        discountService.update(discounts, restaurantUser.getRestaurantId());
        restaurantService.sendToSearchingEngine(restaurantUser.getRestaurantId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/discount", method = RequestMethod.GET, produces = "application/json")
    public List<DiscountDomain> getByRestaurant(@RequestHeader("access-token") String restaurantAccessToken) {
        UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
        return discountService.getByRestaurantId(restaurantUser.getRestaurantId());
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/discount", method = RequestMethod.GET, produces = "application/json")
    public List<DiscountDomain> getByRestaurant(@PathVariable("restaurantId") long restaurantId) {
        return discountService.getByRestaurantId(restaurantId);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/discount/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteDiscount(@RequestHeader("access-token") String restaurantAccessToken,
                                         @PathVariable("id") long id) {
        UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
        discountService.deleteById(id, restaurantUser.getRestaurantId());
        restaurantService.sendToSearchingEngine(restaurantUser.getRestaurantId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
