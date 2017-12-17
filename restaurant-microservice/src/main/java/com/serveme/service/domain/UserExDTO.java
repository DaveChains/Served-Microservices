package com.serveme.service.domain;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public class UserExDTO implements Serializable {

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDigitsId() {
        return digitsId;
    }

    public void setDigitsId(String digitsId) {
        this.digitsId = digitsId;
    }

    public String getLastDeviceId() {
        return lastDeviceId;
    }

    public void setLastDeviceId(String lastDeviceId) {
        this.lastDeviceId = lastDeviceId;
    }

    public String getLastDeviceType() {
        return lastDeviceType;
    }

    public void setLastDeviceType(String lastDeviceType) {
        this.lastDeviceType = lastDeviceType;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public boolean isTestUser() {
        return testUser;
    }

    public void setTestUser(boolean testUser) {
        this.testUser = testUser;
    }

    @Override
    public String toString() {
        return "UserExDTO{" +
                "id=" + id +
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
