package com.serveme.service.service.impl;

import com.serveme.service.domain.DeviceInformation;
import com.serveme.service.enums.DeviceType;
import com.serveme.service.service.NotificationService;
import com.serveme.service.util.http.HttpServiceBase;
import com.serveme.service.util.http.HttpServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 20/11/15.
 */
@Service
public class NotificationServiceImpl implements NotificationService {


    Logger logger = Logger.getLogger(NotificationServiceImpl.class.getName());

    @Value("${service.notification.host}")
    private String host;

    @Value("${service.notification.port}")
    private int port;

    //SERVICES PATH
    protected static String SEND_EXPIRED_SESSION = "/notification/expired/restaurant";

    @Override
    public void sendExpiredSession(String lastDeviceId, String lastDeviceType, String deviceId, String deviceType) {
        if (lastDeviceId == null || lastDeviceType == null) return;
        if (lastDeviceId.equals(deviceId) && lastDeviceType.equals(deviceType)) return;

        try {
            DeviceInformation deviceInformation = new DeviceInformation();
            deviceInformation.setDeviceId(lastDeviceId);
            deviceInformation.setDeviceType(DeviceType.valueOf(lastDeviceType.toUpperCase()));

            new HttpServiceBase()
                    .setUrl("http://" + host + ":" + port)
                    .setHttpMethod(HttpMethod.POST)
                    .setBody(deviceInformation)
                    .setPath(SEND_EXPIRED_SESSION)
                    .setReturnedClass(Object.class)
                    .call();
            return;
        } catch (HttpServiceException ex) {
            logger.log(Level.WARNING, "Can't connect to notification service");
        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.toString());
        }
        logger.log(Level.WARNING, "Error");
    }
}
