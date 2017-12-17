package com.millennialslab.served.service.external;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class Digits3P implements Serializable {

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("access_token")
    private DigitsAccessToken accessToken;

    private long id;

    @SerializedName("id_str")
    private String idStr;

    @SerializedName("verification_type")
    private String verification_type;

    @SerializedName("created_at")
    private String createdAt;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DigitsAccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(DigitsAccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public class DigitsAccessToken {


        private String token;

        private String secret;


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }


        @Override
        public String toString() {
            return "DigitsAccessToken{" +
                    "token='" + token + '\'' +
                    ", secret='" + secret + '\'' +
                    '}';
        }
    }
}

