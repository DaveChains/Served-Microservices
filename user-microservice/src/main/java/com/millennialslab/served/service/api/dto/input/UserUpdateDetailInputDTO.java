package com.millennialslab.served.service.api.dto.input;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class UserUpdateDetailInputDTO implements Serializable {

    private String firstName;
    private String email;
    private String surname;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    @Override
    public String toString() {
        return "UserUpdateDetailInputDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}