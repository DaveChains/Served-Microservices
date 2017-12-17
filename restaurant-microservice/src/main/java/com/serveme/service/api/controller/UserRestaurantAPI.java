package com.serveme.service.api.controller;

import com.serveme.service.api.dto.in.SignInDTO;
import com.serveme.service.api.dto.in.UserRestaurantRegistrationDTO;
import com.serveme.service.api.dto.out.DevicesDTO;
import com.serveme.service.domain.RestaurantTab;
import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.domain.UserRestaurantTokenDomain;
import com.serveme.service.service.NotificationService;
import com.serveme.service.service.PaymentService;
import com.serveme.service.service.UserRestaurantService;
import com.serveme.service.util.BaseController;
import com.serveme.service.util.GenericHiddenBuilder;
import com.serveme.service.util.StringUtil;
import com.serveme.service.util.exception.http.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Controller
public class UserRestaurantAPI extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserRestaurantAPI.class);

    @Inject
    protected UserRestaurantService userRestaurantService;

    @Inject
    protected NotificationService notificationService;
    
    @Inject
    protected PaymentService paymentService;

    @Value("${admin.token}")
    protected String adminToken;

    @Value("${admin.restaurant}")
    protected long adminRestaurantId;

    @ResponseBody
    @RequestMapping(value = "restaurant/user/{userRestaurantId}", method = RequestMethod.GET, produces = "application/json")
    public UserRestaurantDomain getUserRestaurant(@PathVariable("userRestaurantId") long userRestaurantId) {

        logger.info(String.format("GET : /restaurant/user/%s", userRestaurantId));
        UserRestaurantDomain userRestaurantDomainResult = userRestaurantService.getById(userRestaurantId);
        if (userRestaurantId == adminRestaurantId && userRestaurantDomainResult == null) {
            userRestaurantDomainResult = userRestaurantService.getByRestaurantId(adminRestaurantId);
        }
        return new GenericHiddenBuilder<>(userRestaurantDomainResult).hide("password").build();

    }


    @ResponseBody
    @RequestMapping(value = "restaurant/user", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity createUserRestaurant(@RequestBody UserRestaurantRegistrationDTO registrationDto) {

        if (StringUtil.nullEmpty(registrationDto.getRestaurantName())) {
            return new ResponseEntity<>("RestaurantDomain name not provided", HttpStatus.BAD_REQUEST);
        }

        if (registrationDto.getUser() == null) {
            return new ResponseEntity<>("User email not provided", HttpStatus.BAD_REQUEST);
        }

        if (StringUtil.nullEmpty(registrationDto.getUser().getEmail())) {
            return new ResponseEntity<>("User email not provided", HttpStatus.BAD_REQUEST);
        }

        if (StringUtil.nullEmpty(registrationDto.getUser().getPassword())) {
            return new ResponseEntity<>("User password not provided", HttpStatus.BAD_REQUEST);
        }

        logger.debug(String.format("POST : /restaurant/user\n%s", registrationDto.toString()));
        long userRestaurantId = userRestaurantService.createUserRestaurant(registrationDto.getRestaurantName(), registrationDto.getUser());
        return new ResponseEntity<>(userRestaurantId, HttpStatus.CREATED);

    }

    @ResponseBody
    @RequestMapping(value = "restaurant/{restaurantId}/user", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Long> createUserRestaurant(@PathVariable("restaurantId") long restaurantId, @RequestBody UserRestaurantDomain userRestaurantDomain) {
        logger.debug(String.format("PUT : /restaurant/%s/user\n", userRestaurantDomain.toString()));
        userRestaurantDomain.setRestaurantId(restaurantId);
        long userRestaurantId = userRestaurantService.createUserRestaurant(restaurantId, userRestaurantDomain);
        return new ResponseEntity<>(userRestaurantId, HttpStatus.CREATED);

    }

    @ResponseBody
    @RequestMapping(value = "restaurant/user", method = RequestMethod.PUT, produces = "application/json")
    public void updateUserRestaurant(@RequestBody UserRestaurantDomain userRestaurantDomain) {

        logger.debug(String.format("PUT : /restaurant/user\n%s", userRestaurantDomain.toString()));
        userRestaurantService.updateUserRestaurant(userRestaurantDomain);

    }


    @ResponseBody
    @RequestMapping(value = "restaurant/user/{userRestaurantId}", method = RequestMethod.DELETE, produces = "application/json")
    public void deleteUserRestaurant(@PathVariable("userRestaurantId") long userRestaurantId) {

        logger.debug(String.format("DELETE : /restaurant/%s/user/%s", userRestaurantId));
        userRestaurantService.remove(userRestaurantId);

    }

    @ResponseBody
    @RequestMapping(value = "restaurant/user/signin", method = RequestMethod.POST, produces = "application/json")
    public UserRestaurantTokenDomain signIn(@RequestBody SignInDTO signInDto) {

        logger.debug(String.format("POST : /restaurant/user/signin %s", signInDto.toString()));

        UserRestaurantTokenDomain userRestaurantTokenDomain = userRestaurantService.signIn(signInDto.getEmail(), signInDto.getPassword());
        if (userRestaurantTokenDomain != null) {
            UserRestaurantDomain userRestaurantDomain = getUserRestaurant(userRestaurantTokenDomain.getUserId());
            if (signInDto.getDeviceId() != null && signInDto.getDeviceId().length() > 0
                    && signInDto.getDeviceType() != null && signInDto.getDeviceType().length() > 0) {
                notificationService.sendExpiredSession(userRestaurantDomain.getLastDeviceId(), userRestaurantDomain.getLastDeviceType(),
                        signInDto.getDeviceId(), signInDto.getDeviceType());
                userRestaurantDomain.setLastDeviceId(signInDto.getDeviceId());
                userRestaurantDomain.setLastDeviceType(signInDto.getDeviceType());
            }
            userRestaurantService.updateUserRestaurant(userRestaurantDomain);
            if (userRestaurantDomain.isAdminAccount()) {
                return userRestaurantService.adminSignIn(adminToken, adminRestaurantId);
            }
        }
        return userRestaurantTokenDomain;
    }

    @ResponseBody
    @RequestMapping(value = "restaurant/user/tabs", method = RequestMethod.GET, produces = "application/json")
    public List<RestaurantTab> getRestaurantTabs(@RequestHeader("access-token") String accessToken) {

        UserRestaurantDomain userRestaurantDomain = userRestaurantService.authenticate(accessToken);
        if (userRestaurantDomain != null) {
            return paymentService.getRestaurantsTabs(userRestaurantDomain.getRestaurantId());
        } else {
        	throw new RuntimeException("Wrong access token");
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "restaurant/user/auth", method = RequestMethod.POST, produces = "application/json")
    public UserRestaurantDomain authentication(@RequestBody String accessToken) {


        logger.debug(String.format("POST : /restaurant/user/auth\n\taccess token : %s", accessToken));
        UserRestaurantDomain userRestaurantDomain = userRestaurantService.authenticate(accessToken);
        if (userRestaurantDomain != null) {
            return new GenericHiddenBuilder<>(userRestaurantDomain).hide("password").build();
        } else {
            throw new AuthenticationException("Authentication Exception for token " + accessToken, "Token not authorized");
        }
    }


    @ResponseBody
    @RequestMapping(value = "restaurant/user/device", method = RequestMethod.POST, produces = "application/json")
    public DevicesDTO getLastDevice(@RequestBody long restaurantId) {


        logger.debug(String.format("POST : /restaurant/user/device: ", restaurantId));
        UserRestaurantDomain userRestaurantDomain = userRestaurantService.getByRestaurantId(restaurantId);
        if (userRestaurantDomain != null) {
            return new DevicesDTO(userRestaurantDomain);
        } else {
            throw new RuntimeException("Authentication error");
        }
    }

    @ResponseBody
    @RequestMapping(value = "restaurant/admin/device", method = RequestMethod.POST, produces = "application/json")
    public DevicesDTO getAdminDevices(@RequestBody Object body) {

        logger.debug(String.format("POST : /restaurant/admin/device "));
        List<UserRestaurantDomain> adminUsers = userRestaurantService.getAdminUsers();
        if (adminUsers != null) {
            return new DevicesDTO(adminUsers);
        } else {
            throw new RuntimeException("Authentication error");
        }
    }

}
