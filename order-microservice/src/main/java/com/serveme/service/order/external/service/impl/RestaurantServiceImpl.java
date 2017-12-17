package com.serveme.service.order.external.service.impl;

import com.google.gson.reflect.TypeToken;
import com.serveme.service.order.api.errors.Errors;
import com.serveme.service.order.domain.DiscountDomain;
import com.serveme.service.order.domain.Location;
import com.serveme.service.order.domain.RestaurantDistanceData;
import com.serveme.service.order.domain.RestaurantOrder;
import com.serveme.service.order.external.dto.output.DevicesDTO;
import com.serveme.service.order.external.dto.output.RestaurantExDTO;
import com.serveme.service.order.external.dto.output.RestaurantsIdsDTO;
import com.serveme.service.order.external.dto.output.UserRestaurantExDTO;
import com.serveme.service.order.external.service.RestaurantService;
import com.serveme.service.order.util.api.httpExceptions.AuthenticationException;
import com.serveme.service.order.util.api.httpExceptions.InternalServerException;
import com.serveme.service.order.util.http.HttpServiceBase;
import com.serveme.service.order.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 20/11/15.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    //SERVICES PATH
    protected final static String GET_RESTAURANT_FULL = "/restaurant/{restaurantId}/full";
    protected final static String GET_RESTAURANT = "/restaurant/{restaurantId}";
    protected final static String AUTH_RESTAURANT_USER = "restaurant/user/auth";
    protected final static String RESTAURANT_DEVICE = "restaurant/user/device";
    protected final static String ADMIN_DEVICE = "restaurant/admin/device";
    protected final static String RESTAURANT_ALL = "restaurant/all";
    protected final static String RESTAURANT_DISCOUNTS = "restaurant/{restaurantId}/discount";
    protected final static String VALIDATE_DISTANCE = "restaurant/{restaurantId}/valid_distance";
    protected static final String IS_RESTAURANT_OPEN = "restaurant/{restaurantId}/isopen/{time}";
    protected static final String IS_RESTAURANT_ONLINE = "restaurant/{restaurantId}/online";

    Logger logger = Logger.getLogger(RestaurantServiceImpl.class.getName());
    @Value("${service.restaurant.host}")
    private String host;
    @Value("${service.restaurant.port}")
    private int port;

    @Override
    public RestaurantExDTO getRestaurant(long id) {
        try {
            return (RestaurantExDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_RESTAURANT_FULL)
                    .setPathParam("restaurantId", String.valueOf(id))
                    .setReturnedClass(RestaurantExDTO.class)
                    .call();
        } catch (HttpServiceException ex) {
            throw new InternalServerException("Error connecting to RESTAURANT SERVICE " + ex.getMessage(), Errors.INTERNAL_SERVER_ERROR.getError());
        }
    }

    @Override
    public RestaurantOrder getRestaurantData(long id) {
        try {
            return (RestaurantOrder) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_RESTAURANT)
                    .setPathParam("restaurantId", String.valueOf(id))
                    .setReturnedClass(RestaurantOrder.class)
                    .call();
        } catch (HttpServiceException ex) {
            throw new InternalServerException("Error connecting to RESTAURANT SERVICE " + ex.getMessage(), Errors.INTERNAL_SERVER_ERROR.getError());
        }
    }

    @Override
    public UserRestaurantExDTO authRestaurant(String accessToken) {
        try {
            return (UserRestaurantExDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(AUTH_RESTAURANT_USER)
                    .setBody(accessToken)
                    .setReturnedClass(UserRestaurantExDTO.class)
                    .call();
        } catch (HttpServiceException ex) {
            if (ex.getStatusCode() == 401) {
                throw new AuthenticationException("Authentication error retrieving the user by token : " + accessToken + "\n" + ex.getMessage(),
                        Errors.AUTHENTICATION.getError());
            } else {
                throw new InternalServerException("Http error connecting to User service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                        Errors.INTERNAL_SERVER_ERROR.getError());
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public DevicesDTO getRestaurantDevice(long restaurantId) {
        try {
            return (DevicesDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(RESTAURANT_DEVICE)
                    .setBody(restaurantId)
                    .setReturnedClass(DevicesDTO.class)
                    .call();
        } catch (Exception ex) {

            logger.log(Level.WARNING, "No devices found for restaurant");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public RestaurantsIdsDTO getAllRestaurants() {
        try {
            return (RestaurantsIdsDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(RESTAURANT_ALL)
                    .setReturnedClass(RestaurantsIdsDTO.class)
                    .call();
        } catch (Exception ex) {

            return null;
        }
    }

    @Override
    public boolean isRestaurantOpen(long restaurantId, long time) {
        try {
            return (Boolean) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(IS_RESTAURANT_OPEN)
                    .setPathParam("restaurantId", String.valueOf(restaurantId))
                    .setPathParam("time", String.valueOf(time))
                    .setReturnedClass(Boolean.class)
                    .call();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean isRestaurantOnline(long restaurantId) {
        try {
            return (Boolean) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(IS_RESTAURANT_ONLINE)
                    .setPathParam("restaurantId", String.valueOf(restaurantId))
                    .setReturnedClass(Boolean.class)
                    .call();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<DiscountDomain> getDiscounts(long restaurantId) {
        try {
            return (List<DiscountDomain>) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPathParam("restaurantId", String.valueOf(restaurantId))
                    .setPath(RESTAURANT_DISCOUNTS)
                    .setReturnedClass(new TypeToken<List<DiscountDomain>>() {
                    }.getType())
                    .call();
        } catch (Exception ex) {

            return new ArrayList<>();
        }
    }

    @Override
    public RestaurantDistanceData getValidDistance(long restaurantId, Location location) {
        try {
            return (RestaurantDistanceData) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPathParam("restaurantId", String.valueOf(restaurantId))
                    .setPath(VALIDATE_DISTANCE + "?lat=" + location.getLat() + "&lon=" + location.getLon())
                    .setReturnedClass(RestaurantDistanceData.class)
                    .call();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public DevicesDTO getAdminDevices() {
        try {
            return (DevicesDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(ADMIN_DEVICE)
                    .setReturnedClass(DevicesDTO.class)
                    .call();
        } catch (Exception ex) {
            logger.log(Level.WARNING, "No devices found for admins");
            return null;
        }
    }
}
