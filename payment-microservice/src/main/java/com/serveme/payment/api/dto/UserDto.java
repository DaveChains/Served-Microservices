package com.serveme.payment.api.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

public class UserDto {

    private long id;

    private String firstName;

    private String surname;

    private String phoneNumber;

    private String email;

    private String digitsId;

    private String lastDeviceId;

    private String lastDeviceType;

    private String promoCode;
    
    private boolean testUser;

    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public String getDigitsId() {
        return digitsId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public long getId() {
        return id;
    }

    public String getLastDeviceId() {
        return lastDeviceId;
    }

    public String getLastDeviceType() {
        return lastDeviceType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public String getSurname() {
        return surname;
    }

    public boolean isTestUser() {
        return testUser;
    }


    @Override
    public String toString() {
        return "UserDto{" +
                "customerId='" + customerId + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", digitsId='" + digitsId + '\'' +
                ", lastDeviceId='" + lastDeviceId + '\'' +
                ", lastDeviceType='" + lastDeviceType + '\'' +
                ", promoCode='" + promoCode + '\'' +
                ", testUser=" + testUser +
                '}';
    }
}
