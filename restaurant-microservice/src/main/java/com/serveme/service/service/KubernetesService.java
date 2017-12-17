package com.serveme.service.service;

import feign.Headers;
import feign.RequestLine;

/**
 * Created by Davids-iMac on 16/05/16.
 */
public interface KubernetesService {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /order/test")
    String getTest();

}
