package com.serveme.service.searching.service.impl;

import com.serveme.service.searching.domain.UserExDTO;
import com.serveme.service.searching.service.UserService;
import com.serveme.service.searching.util.http.HttpServiceBase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

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

    @Override
    public UserExDTO getUser(String accessToken) {

        try {
            return (UserExDTO) new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath(GET_USER_BY_ACCESSTOKEN)
                    .setHeader("access-token", accessToken)
                    .setReturnedClass(UserExDTO.class)
                    .setNonElastic(true)
                    .call();

        } catch (Exception ex) {
            throw ex;
        }

    }

}
