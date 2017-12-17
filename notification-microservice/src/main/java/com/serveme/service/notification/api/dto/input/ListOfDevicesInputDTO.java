package com.serveme.service.notification.api.dto.input;

import com.serveme.service.notification.domain.DeviceInformation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DavidChains on 7/12/15.
 */
public class ListOfDevicesInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DeviceInformation> deviceIds;
    private String arrivalAt;
    private String restaurantName;

    public List<DeviceInformation> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<DeviceInformation> deviceIds) {
        this.deviceIds = deviceIds;
    }

    @Override
    public String toString() {
        return "ListOfDevicesInputDTO [deviceIds=" + deviceIds +
                ", restaurantName=" + restaurantName +
                ", arrivalAt=" + arrivalAt + "]";
    }

    public String getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(String arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void addDeviceInformation(DeviceInformation deviceInformation) {
        deviceIds.add(deviceInformation);
    }
}
