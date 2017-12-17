package com.millennialslab.served.service.service.impl;

import com.millennialslab.served.service.external.Digits3P;
import com.millennialslab.served.service.service.DigitsExternalService;
import com.millennialslab.served.service.util.exception.http.AuthenticationException;
import com.millennialslab.served.service.util.http.HttpServiceBase;
import com.millennialslab.served.service.exceptions.DigitsAuthenticationException;
import com.millennialslab.served.service.exceptions.DigitsBadRequestException;
import com.millennialslab.served.service.util.exception.http.BadRequestException;
import org.springframework.stereotype.Service;

/**
 * Created by Davids-iMac on 15/11/15.
 */
@Service
public class DigitsExternalServiceImpl implements DigitsExternalService {


    @Override
    public Digits3P authenticate(String ServiceProvider, String authorizationHeader){

        try{
            return  (Digits3P)new HttpServiceBase()
                    .setUrl(ServiceProvider)
                    .setPort(80)
                    .setHeader("Authorization", authorizationHeader)
                    .setReturnedClass(Digits3P.class)
                    .call();

        }catch(AuthenticationException ex){
            throw  new DigitsAuthenticationException();

        }catch(BadRequestException ex){
            throw  new DigitsBadRequestException();

        }catch(Exception ex){
            throw new RuntimeException(ex.getMessage());

        }



    }


}
