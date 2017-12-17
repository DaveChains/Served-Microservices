package com.serveme.service.notification.external.service.impl;

import com.serveme.service.notification.api.errors.Errors;
import com.serveme.service.notification.external.dto.input.GcmDTO;
import com.serveme.service.notification.external.dto.input.GcmMessageDTO;
import com.serveme.service.notification.external.dto.output.GcmResponseDTO;
import com.serveme.service.notification.external.service.GCMService;
import com.serveme.service.notification.util.api.httpExceptions.InternalServerException;
import com.serveme.service.notification.util.http.HttpServiceBase;
import com.serveme.service.notification.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by DavidChains on 20/12/15.
 */
@Service
public class GCMServiceImpl implements GCMService {

    Logger logger = Logger.getLogger(GCMServiceImpl.class.getName());

    @Value("${service.gcm.host}")
    private String host;

    @Value("${service.gcm.appId}")
    private String appId;

    @Value("${service.gcm.apiKey}")
    private String apiKey;

    @Value("${service.gcm.apiKeyRestaurant}")
    private String apiKeyRest;

    @Override //androif
    public GcmResponseDTO sendUserPushNotification(String deviceId, String orderId, String type, String restaurant, String arrivalAt) {
        return sendNotification(apiKey, new GcmDTO(deviceId, orderId, type, restaurant, arrivalAt));
    }

    @Override
    public GcmResponseDTO sendRestaurantPushNotification(String deviceId, String orderId, String type) {
        return sendNotification(apiKeyRest, new GcmDTO(deviceId, orderId, type, null, null));
    }

    @Override
    public GcmResponseDTO sendUserPushNotification(String deviceId, String title, String message, String type) {
        return sendNotification(apiKey, new GcmMessageDTO(deviceId, title, message, type));
    }

    @Override
    public GcmResponseDTO sendRestaurantPushNotification(String deviceId, String title, String message, String type) {
        return sendNotification(apiKeyRest, new GcmMessageDTO(deviceId, title, message, type));
    }

    @Override
    public boolean isReachable() {
        try {
            new HttpServiceBase()
                    .setUrl(host)
                    .setHttpMethod(HttpMethod.GET)
                    .setPath("/gcm/send")
                    .call();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private GcmResponseDTO sendNotification(String key, Object body) {
        try {
            return (GcmResponseDTO) new HttpServiceBase()
                    .setUrl(host)
                    .setHttpMethod(HttpMethod.POST)
                    .setBody(body)
                    .setPath("/gcm/send")
                    .setHeader("Authorization", "key=" + key)
                    .setReturnedClass(GcmResponseDTO.class)
                    .call();
        } catch (HttpServiceException ex) {
            throw new InternalServerException("Http error connecting to GCM service\nResposeStatus:" + ex.getStatusCode() + "\nmessage" + ex.getMessage(),
                    Errors.INTERNAL_SERVER_ERROR.getError());
        } catch (Exception ex) {
            throw ex;
        }
    }
}
