package com.serveme.service.notification.external.service.impl;

import com.serveme.service.notification.api.errors.Errors;
import com.serveme.service.notification.external.dto.input.ParseDTO;
import com.serveme.service.notification.external.dto.output.ResponseDTO;
import com.serveme.service.notification.external.service.ParseService;
import com.serveme.service.notification.util.api.httpExceptions.InternalServerException;
import com.serveme.service.notification.util.http.HttpServiceBase;
import com.serveme.service.notification.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by DavidChains on 10/12/15.
 */
@Service
public class ParseServiceImpl implements ParseService {


    static private String pushPath = "/1/push";
    Logger logger = Logger.getLogger(ParseServiceImpl.class.getName());
    @Value("${service.parse.host}")
    private String host;
    @Value("${service.parse.appId}")
    private String appId;
    @Value("${service.parse.apiKey}")
    private String apiKey;

    @Value("${service.parse.appIdRest}")
    private String appIdRest;
    @Value("${service.parse.apiKeyRest}")
    private String apiKeyRest;

    @Value("${service.parse.status}")
    private String statusUrl;

    @Override
    public ResponseDTO sendUserPushNotification(ParseDTO notificationData) {
        return sendNotification(notificationData, appId, apiKey);
    }

    @Override
    public ResponseDTO sendRestaurantPushNotification(ParseDTO notificationData) {
        return sendNotification(notificationData, appIdRest, apiKeyRest);
    }

    @Override
    public boolean isReachable() {
        try {
            new HttpServiceBase()
                    .setUrl(statusUrl)
                    .setHttpMethod(HttpMethod.GET)
                    .call();
            return true;
        } catch (HttpServiceException ex) {
            return false;
        }
    }

    private ResponseDTO sendNotification(ParseDTO notificationData, String id, String key) {
        try {
            return (ResponseDTO) new HttpServiceBase()
                    .setUrl(host)
                    .setHttpMethod(HttpMethod.POST)
                    .setBody(notificationData)
                    .setPath(pushPath)
                    .setHeader("X-Parse-Application-Id", id)
                    .setHeader("X-Parse-REST-API-Key", key)
                    .setReturnedClass(ResponseDTO.class)
                    .call();
        } catch (HttpServiceException ex) {
            throw new InternalServerException("Http error connecting to Parse service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                    Errors.INTERNAL_SERVER_ERROR.getError());
        } catch (Exception ex) {
            throw ex;
        }
    }
}
