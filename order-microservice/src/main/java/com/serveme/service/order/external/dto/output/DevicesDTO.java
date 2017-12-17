package com.serveme.service.order.external.dto.output;

import com.serveme.service.order.domain.DeviceInformation;
import com.serveme.service.order.domain.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DavidChains on 7/12/15.
 */
public class DevicesDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DeviceInformation> deviceIds;
    private String restaurantName;
    private String arrivalAt;

    public static DevicesDTO parse(List<UserExDTO> userExDTOs, Order order) {
        DevicesDTO devicesDTO = new DevicesDTO();
        devicesDTO.setRestaurantName(order.getRestaurant().getName());
        devicesDTO.setArrivalAt(order.getArrivalAt());
        List<DeviceInformation> deviceIds = new ArrayList<DeviceInformation>();
        for (UserExDTO user : userExDTOs) {
            deviceIds.add(new DeviceInformation(user.getLastDeviceId(), user.getLastDeviceType()));
        }
        devicesDTO.setDeviceIds(deviceIds);
        return devicesDTO;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(String arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public List<DeviceInformation> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<DeviceInformation> deviceIds) {
        this.deviceIds = deviceIds;
    }

    @Override
    public String toString() {
        return "DevicesDTO [deviceIds=" + deviceIds +
                ", restaurantName=" + restaurantName +
                ", arrivalAt=" + arrivalAt + "]";
    }
}
