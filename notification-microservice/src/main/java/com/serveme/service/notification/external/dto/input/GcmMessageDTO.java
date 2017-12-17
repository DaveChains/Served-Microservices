package com.serveme.service.notification.external.dto.input;

/**
 * Created by Edgar on 12/20/2015.
 */
public class GcmMessageDTO {
    private String to;
    private Data data;

    public GcmMessageDTO(String deviceId, String title, String message, String type) {
        this.to = deviceId;
        this.data = new Data(title, message, type);
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
        String title;
        String message;
        String type;

        public Data(String title, String message, String type) {
            this.title = title;
            this.message = message;
            this.type = type;
        }
    }
}
