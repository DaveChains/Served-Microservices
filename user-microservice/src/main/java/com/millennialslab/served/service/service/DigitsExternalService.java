package com.millennialslab.served.service.service;

import com.millennialslab.served.service.external.Digits3P;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public interface DigitsExternalService {


    Digits3P authenticate(String ServiceProvider, String authorizationHeader);
}
