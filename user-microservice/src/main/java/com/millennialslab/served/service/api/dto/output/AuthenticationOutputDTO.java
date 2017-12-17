package com.millennialslab.served.service.api.dto.output;

import com.millennialslab.served.service.api.dto.input.PaymentInfoDto;
import com.millennialslab.served.service.domain.AccessTokenDomain;
import com.millennialslab.served.service.domain.UserDomain;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class AuthenticationOutputDTO {

    private AccessTokenDomain token;
    private UserDomain profile;
    private PaymentInfoDto paymentInfo;

    public AccessTokenDomain getToken() {
        return token;
    }

    public void setToken(AccessTokenDomain token) {
        this.token = token;
    }

    public UserDomain getProfile() {
        return profile;
    }

    public void setProfile(UserDomain profile) {
        this.profile = profile;
    }

    public PaymentInfoDto getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfoDto paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    @Override
    public String toString() {
        return "AuthenticationOutputDTO{" +
                "token=" + token +
                ", profile=" + profile +
                ", paymentInfo=" + paymentInfo +
                '}';
    }
}
