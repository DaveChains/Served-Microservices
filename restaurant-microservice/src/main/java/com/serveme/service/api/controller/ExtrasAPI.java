package com.serveme.service.api.controller;

import com.serveme.service.domain.ExtraDomain;
import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.service.ExtrasService;
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

@Controller
public class ExtrasAPI extends BaseController {

    Logger logger = Logger.getLogger(ExtrasAPI.class.getName());

    @Inject
    protected ExtrasService extrasService;

    @Inject
    protected UserRestaurantAPI restaurantAPI;

    @Value("${admin.token}")
    protected String adminToken;

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/extras/{dishId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateExtras(@RequestHeader("access-token") String restaurantAccessToken,
                                       @PathVariable("restaurantId") long restaurantId,
                                       @PathVariable long dishId,
                                       @RequestBody List<ExtraDomain> extras) {

        logger.log(Level.INFO, "PUT : restaurant/extras/" + restaurantId);
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        for (ExtraDomain extra : extras) {
            extra.setDishId(dishId);
        }
        extrasService.update(extras, restaurantId);
        return new ResponseEntity(HttpStatus.OK);

    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/dish/{dishId}/extras", method = RequestMethod.GET, produces = "application/json")
    public List<ExtraDomain> getByRestaurant(@RequestHeader("access-token") String restaurantAccessToken,
                                             @PathVariable("restaurantId") long restaurantId,
                                             @PathVariable("dishId") long dishId) {
        return extrasService.getByRestaurantId(restaurantId, dishId);
    }

    @ResponseBody
    @RequestMapping(value = "/restaurant/{restaurantId}/extras/{extraId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteExtras(@RequestHeader("access-token") String restaurantAccessToken,
                                       @PathVariable("restaurantId") long restaurantId,
                                       @PathVariable("extraId") long id) {
        if (!restaurantAccessToken.equals(adminToken)) {
            UserRestaurantDomain restaurantUser = restaurantAPI.authentication(restaurantAccessToken);
            restaurantId = restaurantUser.getRestaurantId();
        }
        extrasService.deleteById(id, restaurantId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
