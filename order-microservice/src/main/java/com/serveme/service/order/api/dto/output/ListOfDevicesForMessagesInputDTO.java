package com.serveme.service.order.api.dto.output;

import java.io.Serializable;
import java.util.List;

import com.serveme.service.order.domain.DeviceInformation;

public class ListOfDevicesForMessagesInputDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DeviceInformation> deviceIds;
    private String title;
    private String message;
    private long code;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public List<DeviceInformation> getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(List<DeviceInformation> deviceIds) {
        this.deviceIds = deviceIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addDeviceInformation(DeviceInformation deviceInformation) {
        deviceIds.add(deviceInformation);
    }
}
