package com.millennialslab.served.service.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Davids-iMac on 25/10/15.
 */
@Table(name = "USERS")
public class UserDomain implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "digits_id")
    private String digitsId;

    @Column(name = "last_device_id")
    private String lastDeviceId;

    @Column(name = "last_device_type")
    private String lastDeviceType;

    @Column(name = "promo_code")
    private String promoCode;
    
    @Column(name = "test_user")
    private boolean testUser;

    @Column(name = "customer_id")
    private String customerId;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "UserDomain{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", digitsId='" + digitsId + '\'' +
                ", lastDeviceId='" + lastDeviceId + '\'' +
                ", lastDeviceType='" + lastDeviceType + '\'' +
                ", promoCode='" + promoCode + '\'' +
                '}';
    }

    public UserDomain removeNullFields() {
        if (firstName == null || firstName.equals("null"))
            firstName = "";
        if (surname == null || surname.equals("null"))
            surname = "";
        if (email == null || email.equals("null"))
            email = "";
        return this;
    }
}
