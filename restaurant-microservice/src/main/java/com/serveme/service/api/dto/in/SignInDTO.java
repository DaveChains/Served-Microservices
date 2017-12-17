package com.serveme.service.api.dto.in;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 23/10/15.
 */
public class SignInDTO implements Serializable {

    private String email;

    private String password;

    private String deviceType;

    private String deviceId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
        return "SignInDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
