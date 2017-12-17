package com.serveme.service.api.controller;

import com.serveme.service.domain.HolidayDomain;
import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.service.HolidayService;
import com.serveme.service.service.RestaurantService;
import com.serveme.service.util.BaseController;
import org.springframework.beans.factory.annotation.Value;
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
public class HolidayAPI extends BaseController {

    Logger logger = Logger.getLogger(HolidayAPI.class.getName());

    @Inject
    protected HolidayService holidayService;

    @Inject
    protected RestaurantService restaurantService;

    @Inject
    protected UserRestaurantAPI restaurantAPI;

    @Value("${admin.token}")
    protected String adminToken;

    @Deprecated
    @ResponseBody
    @RequestMapping(value = "/restaurant/holiday/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Long> createHoliday(@RequestHeader("access-token") String restaurantAccessToken,
                                              @RequestBody HolidayDomain holiday) {
        logger.log(Level.INFO, "POST : restaurant/holiday/create");
        UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
        holiday.setRestaurantId(restaurantUser.getRestaurantId());
        long id = holidayService.create(holiday);
        restaurantService.sendToSearchingEngine(restaurantUser.getRestaurantId());
        return new ResponseEntity<Long>(id, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/holiday", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateHoliday(@RequestHeader("access-token") String restaurantAccessToken,
                                        @PathVariable("restaurantId") long restaurantId,
                                        @RequestBody List<HolidayDomain> holidays) {
        logger.log(Level.INFO, "PUT : restaurant/holiday");
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        holidayService.update(holidays, restaurantId);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/holiday", method = RequestMethod.GET, produces = "application/json")
    public List<HolidayDomain> getByRestaurant(@RequestHeader("access-token") String restaurantAccessToken,
                                               @PathVariable("restaurantId") long restaurantId) {
        logger.log(Level.INFO, "GET : restaurant/holiday");
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        return holidayService.getByRestaurantId(restaurantId);

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/holiday/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteById(@RequestHeader("access-token") String restaurantAccessToken,
                                     @PathVariable("restaurantId") long restaurantId,
                                     @PathVariable("id") long id) {
        logger.log(Level.INFO, "DELETE : restaurant/holiday");
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        holidayService.removeById(id, restaurantId);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
