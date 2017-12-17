package com.serveme.service.service.impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.serveme.service.domain.SearchingEngineRestaurantDomain;
import com.serveme.service.service.OrderService;
import com.serveme.service.util.http.HttpServiceBase;
import com.serveme.service.util.http.HttpServiceException;

/**
 * Created by DavidChains.
 */
@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = Logger.getLogger(SearchingEngineServiceImpl.class.getName());

    @Value("${service.order.host}")
    private String host;

    @Value("${service.order.port}")
    private int port;


    //SERVICES PATH
    protected static String COUNT_ORDERS = "/internal/order/countByUser";
    
    @SuppressWarnings("unchecked")
    @Override
	public List<List<Long>> countOrdersByUser(long userId, List<Long> restaurantIds){
        try {
            return (List<List<Long>>) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setPath(COUNT_ORDERS)
                    .setHeader("userId", String.valueOf(userId))
                    .setBody(restaurantIds)
                    .setReturnedClass(new TypeToken<List<List<Long>>>(){}.getType())
                    .call();
        } catch (HttpServiceException ex) {
            logger.log(Level.SEVERE, "ERROR connecting to order service");
            //TODO register the failure
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure
        }
        return null;
    }


    @SuppressWarnings("unchecked")
    @Override
    public String getTest(){
            logger.log(Level.INFO, String.format("Calling to %s", "http://" + host + ":" + port));
            return (String) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath("order/test")
                    .setReturnedClass(String.class)
                    .call();
    }
}