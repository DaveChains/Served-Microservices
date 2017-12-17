package com.serveme.service.notification.external.service;

import com.serveme.service.notification.external.dto.output.GcmResponseDTO;

/**
 * Created by DavidChains on 20/12/15.
 */
public interface GCMService {
    GcmResponseDTO sendUserPushNotification(String deviceId, String orderId, String type, String restaurant, String arrivalDate);

    GcmResponseDTO sendRestaurantPushNotification(String deviceId, String orderId, String type);

    GcmResponseDTO sendUserPushNotification(String deviceId, String title, String message, String type);

    GcmResponseDTO sendRestaurantPushNotification(String deviceId, String title, String message, String type);

    boolean isReachable();
}
