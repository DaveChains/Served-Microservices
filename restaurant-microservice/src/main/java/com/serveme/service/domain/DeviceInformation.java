package com.serveme.service.domain;


import com.serveme.service.enums.DeviceType;

import java.io.Serializable;


/**
 * Created by DavidChains on 8/12/15.
 */

public class DeviceInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    private String deviceId;
    private DeviceType deviceType;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
        result = prime * result + ((deviceType == null) ? 0 : deviceType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeviceInformation other = (DeviceInformation) obj;
        if (deviceId == null) {
            if (other.deviceId != null)
                return false;
        } else if (!deviceId.equals(other.deviceId))
            return false;
        if (deviceType != other.deviceType)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DeviceInformation [deviceId=" + deviceId + ", deviceType=" + deviceType + "]";
    }


}
