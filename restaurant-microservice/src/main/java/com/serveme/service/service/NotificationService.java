package com.serveme.service.service;

public interface NotificationService {
    void sendExpiredSession(String lastDeviceId, String lastDeviceType, String deviceId, String deviceType);
}
