package com.millennialslab.served.service.service.impl;

import com.millennialslab.served.service.util.http.HttpServiceBase;
import com.millennialslab.served.service.util.http.HttpServiceException;
import com.millennialslab.served.service.domain.DeviceInformation;
import com.millennialslab.served.service.enums.DeviceType;
import com.millennialslab.served.service.service.NotificationService;
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

    //SERVICES PATH
    protected static String SEND_EXPIRED_SESSION = "/notification/expired/user";
    Logger logger = Logger.getLogger(NotificationServiceImpl.class.getName());
    @Value("${service.notification.host}")
    private String host;
    @Value("${service.notification.port}")
    private int port;

    @Override
    public void sendExpiredSession(String lastDeviceId, String lastDeviceType, String deviceId, String deviceType) {
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
