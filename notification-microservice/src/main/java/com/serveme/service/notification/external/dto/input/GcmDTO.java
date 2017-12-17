package com.serveme.service.notification.external.dto.input;

/**
 * Created by Edgar on 12/20/2015.
 */
public class GcmDTO {
    private String to;
    private Data data;

    public GcmDTO(String deviceId, String orderId, String action, String restaurant, String arrivalAt) {
        this.to = deviceId;
        this.data = new Data(orderId, action, restaurant, arrivalAt);
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    private class Data {
        String orderId;
        String action;
        String restaurant;
        String arrivalAt;

        public Data(String orderId, String action, String restaurant, String arrivalAt) {
            this.orderId = orderId;
            this.action = action;
            this.restaurant = restaurant;
            this.arrivalAt = arrivalAt;
        }
    }
}
