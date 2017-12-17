package com.serveme.service.notification.exceptions;

/**
 * Created by DavidChains on 21/11/15.
 */
public class NotificationCannotBeSentException extends RuntimeException {

    private String deviceId;

    public NotificationCannotBeSentException(String deviceId){
        super();
        this.deviceId = deviceId;
    }


    public String getDeviceId() {
        return deviceId;
    }


	public Object getCurrentStatus() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
