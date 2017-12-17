package com.serveme.service.notification.external.service;

import com.serveme.service.notification.external.dto.input.ParseDTO;
import com.serveme.service.notification.external.dto.output.ResponseDTO;

/**
 * Created by DavidChains on 8/12/15.
 */
public interface ParseService {

    ResponseDTO sendUserPushNotification(ParseDTO notificationData);

    ResponseDTO sendRestaurantPushNotification(ParseDTO notificationData);

    boolean isReachable();
}
