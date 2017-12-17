package com.serveme.service.api.controller;

import com.serveme.service.domain.ScheduleDomain;
import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.service.RestaurantService;
import com.serveme.service.service.ScheduleService;
import com.serveme.service.service.UserRestaurantService;
import com.serveme.service.util.BaseController;
import org.springframework.beans.factory.annotation.Value;
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
public class ScheduleAPI extends BaseController {

    Logger logger = Logger.getLogger(ScheduleAPI.class.getName());

    @Inject
    protected ScheduleService scheduleService;

    @Inject
    protected RestaurantService restaurantService;

    @Inject
    protected UserRestaurantService userRestaurantService;

    @Value("${admin.token}")
    protected String adminToken;

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/schedule", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateSchedules(@RequestHeader("access-token") String restaurantAccessToken,
                                          @PathVariable("restaurantId") long restaurantId,
                                          @RequestBody List<ScheduleDomain> schedules) {
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = userRestaurantService.authenticate(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        logger.log(Level.INFO, "PUT : restaurant/" + restaurantId + "/schedule");
        scheduleService.update(schedules, restaurantId);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/schedule", method = RequestMethod.GET, produces = "application/json")
    public List<ScheduleDomain> getByRestaurant(@RequestHeader("access-token") String restaurantAccessToken,
                                                @PathVariable("restaurantId") long restaurantId) {
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = userRestaurantService.authenticate(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        return scheduleService.getByRestaurantId(restaurantId);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/schedule/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteSchedule(@RequestHeader("access-token") String restaurantAccessToken,
                                         @PathVariable("restaurantId") long restaurantId,
                                         @PathVariable("id") long id) {
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = userRestaurantService.authenticate(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        scheduleService.deleteById(id, restaurantId);
        restaurantService.sendToSearchingEngine(restaurantId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
