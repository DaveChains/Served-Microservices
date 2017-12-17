package com.serveme.service.service.impl;

import com.serveme.service.service.GeolocationService;
import com.serveme.service.transfer.dto.PostcodesIOResponse;
import com.serveme.service.util.http.HttpServiceBase;
import com.serveme.service.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Service
public class GeolocationServiceImpl implements GeolocationService {

    Logger logger = Logger.getLogger(GeolocationServiceImpl.class.getName());

    @Value("${service.searching.engine.host}")
    private String host;

    @Value("${service.searching.engine.port}")
    private int port;


    //SERVICES PATH
    protected static String GET_LOCATION_BY_POSTCODE = "/postcodes/{postcode}";


    public Object getLocationByPostcode(String postcode){
        try{
            Object o = new HttpServiceBase()
                    .setUrl("http://api.postcodes.io")
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_LOCATION_BY_POSTCODE)
                    .setPathParam("postcode",postcode)
                    .setReturnedClass(PostcodesIOResponse.class)
                    .call();
            return o;

        }catch(HttpServiceException ex){
            logger.log(Level.SEVERE, "ERROR getting location for postcode "+postcode);
            //TODO register the failure

        }catch(Exception ex){
            logger.log(Level.SEVERE, ex.getMessage());
            //TODO register the failure

        }
        return null;
    }





}
