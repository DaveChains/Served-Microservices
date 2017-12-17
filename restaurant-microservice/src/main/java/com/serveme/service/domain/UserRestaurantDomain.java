package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Table(name = "RESTAURANT_USERS")
public class UserRestaurantDomain {

    @Column(name="id")
    private long id;

    @Column(name = "restaurant_id")
    private long restaurantId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "last_device_id")
    private String lastDeviceId;

    @Column(name = "last_device_type")
    private String lastDeviceType;

    @Column(name = "created")
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @Column(name = "account_expired")
    private boolean accountExpired;

    @Column(name = "account_locked")
    private boolean accountLocked;

    @Column(name = "password_expired")
    private boolean passwordExpired;

    @Column(name = "account_type")
    private boolean adminAccount;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }


    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public boolean isAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(boolean adminAccount) {
        this.adminAccount = adminAccount;
    }

    @Override
    public String toString() {
        return "UserRestaurantDomain{" +
                "id=" + id +
                ", restaurantId='" + restaurantId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", lastDeviceId='" + lastDeviceId + '\'' +
                ", lastDeviceType='" + lastDeviceType + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", accountExpired=" + accountExpired +
                ", accountLocked=" + accountLocked +
                ", adminAccount=" + adminAccount +
                ", passwordExpired=" + passwordExpired +
                '}';
    }
}
