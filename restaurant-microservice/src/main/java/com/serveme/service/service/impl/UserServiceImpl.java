package com.serveme.service.service.impl;

import com.google.gson.reflect.TypeToken;
import com.serveme.service.domain.UserExDTO;
import com.serveme.service.service.UserService;
import com.serveme.service.util.exception.http.AuthenticationException;
import com.serveme.service.util.http.HttpServiceBase;
import com.serveme.service.util.http.HttpServiceException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 20/11/15.
 */
@Service
public class UserServiceImpl implements UserService {


    Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Value("${service.user.host}")
    private String host;

    @Value("${service.user.port}")
    private int port;


    //SERVICES PATH
    protected static String GET_USER_BY_ACCESSTOKEN = "/user/detail";

    public UserExDTO getUser(String accessToken) {

        try{
            return (UserExDTO)new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_USER_BY_ACCESSTOKEN)
                    .setHeader("access-token", accessToken)
                    .setReturnedClass(UserExDTO.class)
                    .call();

        }catch(HttpServiceException ex){
        	logger.log(Level.WARNING, "Can't connect to user service");
        }
        catch(Exception ex){
        	logger.log(Level.WARNING, ex.toString());
        }
        logger.log(Level.WARNING, "Error");
        return null;
    }
}
