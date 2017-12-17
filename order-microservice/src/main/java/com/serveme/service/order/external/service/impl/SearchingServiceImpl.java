package com.serveme.service.order.external.service.impl;

import com.serveme.service.order.external.service.SearchingService;
import com.serveme.service.order.util.http.HttpServiceBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class SearchingServiceImpl implements SearchingService {

    //SERVICES PATH
    protected static String GET_RESTAURANT_OPEN = "/internal/searching/restaurant/";
    Logger logger = Logger.getLogger(SearchingServiceImpl.class.getName());
    @Value("${service.searching.host}")
    private String host;
    @Value("${service.searching.port}")
    private int port;

    @Override
    public boolean isRestaurantOpen(long restaurantId, long time) {
        try {
            return (Boolean) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_RESTAURANT_OPEN + restaurantId + "/isopen/" + time)
                    .setReturnedClass(Boolean.class)
                    .call();

        } catch (Exception ex) {
            throw ex;
        }
    }
}
