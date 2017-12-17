package com.serveme.payment.service;

import com.serveme.payment.exceptions.AccessTokenException;
import com.serveme.payment.api.dto.UserDto;
import feign.*;
import feign.jackson.JacksonDecoder;

import java.util.List;

public interface UserService {

    static UserService client(String url){
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .target(UserService.class, url);
    }

    default UserDto authenticate(String accessToken){
        try{
            return this.getUserByAccessToken(accessToken);
        }catch(FeignException ex){
            if(ex.status() == 401) throw new AccessTokenException(accessToken);
            throw ex;
        }
    }

    default UserDto authenticateAndMatch(String accessToken, Long id){
        try{
            UserDto user = this.getUserByAccessToken(accessToken);
             return user;
//            throw new AccessTokenException(accessToken);
        }catch(FeignException ex){
            if(ex.status() == 401) throw new AccessTokenException(accessToken);
            throw ex;
        }
    }

    @Headers("Content-Type: application/json")
    @RequestLine("GET /internal/user/_list?id={id}")
    List<UserDto> getUserByIdList(@Param("id") List<Long> id);


    @Headers(value = {"Content-Type: application/json", "access-token: {token}"})
    @RequestLine("GET /user/detail")
    UserDto getUserByAccessToken(@Param("token") String token);


}
