package com.millennialslab.served.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Davids-iMac on 15/11/15.
 */
@Table(name = "USER_TOKENS")
public class AccessTokenDomain implements Serializable{

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "ttl")
    private long ttl;


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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "AccessTokenDomain{" +
                "accessToken=" + accessToken +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", ttl=" + ttl +
                '}';
    }
}
