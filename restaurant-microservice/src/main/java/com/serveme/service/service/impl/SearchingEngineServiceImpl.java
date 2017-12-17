package com.serveme.service.service.impl;

import com.google.gson.reflect.TypeToken;
import com.serveme.service.domain.*;
import com.serveme.service.service.SearchingEngineService;
import com.serveme.service.util.http.HttpServiceBase;
import com.serveme.service.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class SearchingEngineServiceImpl implements SearchingEngineService {

    Logger logger = Logger.getLogger(SearchingEngineServiceImpl.class.getName());

    @Value("${service.searching.engine.host}")
    private String host;

    @Value("${service.searching.engine.port}")
    private int port;


    //SERVICES PATH
    protected static String SEARCH_RESTAURANT = "/internal/searching/restaurant/search";

    protected static String PUT_RESTAURANT = "/internal/searching/restaurant";

    protected static String PUT_CATEGORY = "/internal/searching/restaurant/{restaurantId}/category";

    protected static String DELETE_CATEGORY = "/internal/searching/restaurant/{restaurantId}/category/{categoryId}";

    protected static String PUT_DISH = "/internal/searching/restaurant/{restaurantId}/category/{categoryId}/dish";

    protected static String DELETE_DISH = "/internal/searching/restaurant/{restaurantId}/category/{categoryId}/dish/{dishId}";


    @Override
    public void sendRestaurant(RestaurantDomain restaurant) {
        try {
            Object response = new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.PUT)
                    .setPath(PUT_RESTAURANT)
                    .setBody(restaurant.toElasticSearchObject())
                    .call();
            if (response != null) logger.log(Level.INFO, response.toString());
        } catch (HttpServiceException ex) {
            logger.log(Level.SEVERE, "ERROR connecting to " + HttpMethod.PUT.toString() + " : " + PUT_RESTAURANT + "\n\t" + restaurant.toString());
            //TODO register the failure
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure
        }
    }


    @Override
    public void sendCategory(CategoryDomain category) {
        try {
            new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.PUT)
                    .setPath(PUT_CATEGORY)
                    .setPathParam("restaurantId", String.valueOf(category.getRestaurantId()))
                    .setBody(category.toElasticSearchObject())
                    .call();

        } catch (HttpServiceException ex) {
            logger.log(Level.SEVERE, "ERROR connecting to " + HttpMethod.PUT.toString() + " : " + PUT_CATEGORY + "\n\t" + category.toString());
            //TODO register the failure

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure

        }
    }


    @Override
    public void deleteCategory(long restaurantId, long categoryId) {
        try {
            new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.DELETE)
                    .setPath(DELETE_CATEGORY)
                    .setPathParam("restaurantId", String.valueOf(restaurantId))
                    .setPathParam("categoryId", String.valueOf(categoryId))
                    .call();

        } catch (HttpServiceException ex) {
            logger.log(Level.SEVERE, "ERROR connecting to " + HttpMethod.DELETE.toString() + " : " + DELETE_CATEGORY + "\n\trestaurantId: " + restaurantId + "\n\tcategoryId:" + categoryId);
            //TODO register the failure

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure

        }
    }

    @Override
    public void sendDish(DishDomain dish) {
        try {
            new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.PUT)
                    .setPath(PUT_DISH)
                    .setPathParam("restaurantId", String.valueOf(dish.getRestaurantId()))
                    .setPathParam("categoryId", String.valueOf(dish.getCategoryId()))
                    .setBody(dish.toElasticSearchObject())
                    .call();

        } catch (HttpServiceException ex) {
            logger.log(Level.SEVERE, "ERROR connecting to " + HttpMethod.PUT.toString() + " : " + PUT_DISH + "\n\t" + dish.toString());
            //TODO register the failure

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure

        }
    }

    @SuppressWarnings("unchecked")
    public List<SearchingEngineRestaurantDomain> search(SearchObject search, String accessToken) {
        try {
            return (List<SearchingEngineRestaurantDomain>) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(SEARCH_RESTAURANT)
                    .setHeader("access-token", accessToken)
                    .setBody(search)
                    .setReturnedClass(new TypeToken<List<SearchingEngineRestaurantDomain>>() {
                    }.getType())
                    .call();

        } catch (HttpServiceException ex) {
            logger.log(Level.SEVERE, "ERROR connecting to searching service");
            //TODO register the failure
            throw ex;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure
            throw ex;
        }
    }

    @Override
    public void deleteDish(long restaurantId, long categoryId, long dishId) {
        try {
            new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.DELETE)
                    .setPath(DELETE_DISH)
                    .setPathParam("restaurantId", String.valueOf(restaurantId))
                    .setPathParam("categoryId", String.valueOf(categoryId))
                    .setPathParam("dishId", String.valueOf(dishId))
                    .call();

        } catch (HttpServiceException ex) {
            logger.log(Level.SEVERE, "ERROR connecting to " + HttpMethod.DELETE.toString() + " : " + DELETE_DISH + "\n\trestaurantId: " + restaurantId + "\n\tcategoryId:" + categoryId + "\n\tdishId:" + dishId);
            //TODO register the failure

        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure

        }
    }
}