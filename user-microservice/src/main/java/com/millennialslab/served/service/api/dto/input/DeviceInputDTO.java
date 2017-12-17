package com.millennialslab.served.service.api.dto.input;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class DeviceInputDTO implements Serializable {

    private String deviceType;

    private String deviceId;

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "DeviceInputDTO{" +
                "deviceType='" + deviceType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
