package com.serveme.service.order.external.dto.output;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Davids-iMac on 22/10/15.
 */
public class UserRestaurantExDTO {

    private long id;

    private long restaurantId;

//    private String name;
//
//    private String surname;
//
//    private String email;
//
//    private String password;
//
//    private String lastDeviceId;
//
//    private Timestamp created;
//
//    private Timestamp updated;
//
//    private boolean accountExpired;
//
//    private boolean accountLocked;
//
//    private boolean passwordExpired;


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

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getLastDeviceId() {
//        return lastDeviceId;
//    }
//
//    public void setLastDeviceId(String lastDeviceId) {
//        this.lastDeviceId = lastDeviceId;
//    }
//
//    public Timestamp getCreated() {
//        return created;
//    }
//
//    public void setCreated(Timestamp created) {
//        this.created = created;
//    }
//
//
//    public Timestamp getUpdated() {
//        return updated;
//    }
//
//    public void setUpdated(Timestamp updated) {
//        this.updated = updated;
//    }
//
//    public boolean isAccountExpired() {
//        return accountExpired;
//    }
//
//    public void setAccountExpired(boolean accountExpired) {
//        this.accountExpired = accountExpired;
//    }
//
//    public boolean isAccountLocked() {
//        return accountLocked;
//    }
//
//    public void setAccountLocked(boolean accountLocked) {
//        this.accountLocked = accountLocked;
//    }
//
//    public boolean isPasswordExpired() {
//        return passwordExpired;
//    }
//
//    public void setPasswordExpired(boolean passwordExpired) {
//        this.passwordExpired = passwordExpired;
//    }

    @Override
    public String toString() {
        return "UserRestaurantDomain{" +
                "id=" + id +
                ", restaurantId='" + restaurantId + '\'' +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                ", email='" + email + '\'' +
//                ", lastDeviceId='" + lastDeviceId + '\'' +
//                ", created=" + created +
//                ", updated=" + updated +
//                ", accountExpired=" + accountExpired +
//                ", accountLocked=" + accountLocked +
//                ", passwordExpired=" + passwordExpired +
                '}';
    }
}
