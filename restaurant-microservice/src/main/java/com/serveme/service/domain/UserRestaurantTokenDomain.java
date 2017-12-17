package com.serveme.service.domain;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * Created by Davids-iMac on 22/10/15.
 */
public class UserRestaurantTokenDomain {

    @Column(name="access_token")
    private String accessToken;

    @Column(name="user_id")
    private long userId;

    @Column(name="created")
    private Timestamp created;

    @Column(name="ttl")
    private long ttl;

    @Column(name="device_id")
    private String deviceId;

    private String username;

    private long restaurantId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "UserRestaurantTokenDomain{" +
                "accessToken='" + accessToken + '\'' +
                ", userId=" + userId +
                ", created=" + created +
                ", ttl=" + ttl +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
