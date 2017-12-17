package com.serveme.payment.api.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Davids-iMac on 21/11/15.
 */
public class RestaurantOrderDto implements Serializable {
    private long id;

    private String name;

    private String description;

    private String address;

    private String postcode;

    private String city;

    private String country;

    private BigDecimal locationLat;

    private BigDecimal locationLon;

    private String emailForClients;

    private String phoneForClients;

    private String photoUrl;

    private BigDecimal serviceChargePerc;

    private int maxPeopleByTable;

    public RestaurantOrderDto() {}

    public RestaurantOrderDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(BigDecimal locationLat) {
        this.locationLat = locationLat;
    }

    public BigDecimal getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(BigDecimal locationLon) {
        this.locationLon = locationLon;
    }

    public String getEmailForClients() {
        return emailForClients;
    }

    public void setEmailForClients(String emailForClients) {
        this.emailForClients = emailForClients;
    }

    public String getPhoneForClients() {
        return phoneForClients;
    }

    public void setPhoneForClients(String phoneForClients) {
        this.phoneForClients = phoneForClients;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public BigDecimal getServiceChargePerc() {
        return serviceChargePerc;
    }

    public void setServiceChargePerc(BigDecimal serviceChargePerc) {
        this.serviceChargePerc = serviceChargePerc;
    }

    public int getMaxPeopleByTable() {
        return maxPeopleByTable;
    }

    public void setMaxPeopleByTable(int maxPeopleByTable) {
        this.maxPeopleByTable = maxPeopleByTable;
    }

    @Override
    public String toString() {
        return "RestaurantOrder{" +
                "address='" + address + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", locationLat=" + locationLat +
                ", locationLon=" + locationLon +
                ", emailForClients='" + emailForClients + '\'' +
                ", phoneForClients='" + phoneForClients + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", serviceChargePerc=" + serviceChargePerc +
                '}';
    }
}
