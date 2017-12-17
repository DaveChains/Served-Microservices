package com.serveme.service.service.impl;

import com.serveme.service.service.PaymentService;
import com.google.gson.reflect.TypeToken;
import com.serveme.service.domain.RestaurantTab;
import com.serveme.service.util.http.HttpServiceBase;
import com.serveme.service.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PaymentServiceImpl implements PaymentService {
    protected static final String GET_RESTAURANT_TAB = "/restaurantTabs/{restaurantId}";

    Logger logger = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Value("${service.payment.host}")
    private String host;

    @Value("${service.payment.port}")
    private int port;

    @SuppressWarnings("unchecked")
	@Override
    public List<RestaurantTab> getRestaurantsTabs(long restaurantId) {
        try {
            return (List<RestaurantTab>) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPathParam("restaurantId", String.valueOf(restaurantId))
                    .setPath(GET_RESTAURANT_TAB)
                    .setReturnedClass(new TypeToken<List<RestaurantTab>>(){}.getType())
                    .call();
        } catch (HttpServiceException ex) {
            logger.log(Level.WARNING, "Can't connect to payment service");
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.toString());
        }
        logger.log(Level.WARNING, "Error");
        return null;
    }
}
