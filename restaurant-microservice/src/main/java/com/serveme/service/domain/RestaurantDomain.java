package com.serveme.service.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Davids-iMac on 25/10/15.
 */
@Table(name = "RESTAURANTS")
public class RestaurantDomain implements Serializable {

    @Column(name = "id")
    protected long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "description")
    protected String description;

    @Column(name = "address")
    protected String address;

    @Column(name = "postcode")
    protected String postcode;

    @Column(name = "city")
    protected String city;

    @Column(name = "country")
    protected String country;

    @Column(name = "location_lat")
    protected BigDecimal locationLat;

    @Column(name = "location_lon")
    protected BigDecimal locationLon;

    @Column(name = "email_for_clients")
    protected String emailForClients;

    @Column(name = "email_for_us")
    protected String emailForUs;

    @Column(name = "phone_for_clients")
    protected String phoneForClients;

    @Column(name = "phone_for_us")
    protected String phoneForUs;

    @Column(name = "creation_date")
    protected Timestamp creationDate;

    @Column(name = "update_date")
    protected Timestamp updateDate;

    @Column(name = "published")
    protected boolean pusblished;

    @Column(name = "owner_id")
    protected long ownerId;

    @Column(name = "photo_url")
    protected String photoUrl;

    @Column(name = "stripe_id")
    protected String stripeId;

    @Column(name = "service_charge_perc")
    protected String serviceChargePerc;

    @Column(name = "live_restaurant")
    protected boolean liveRestaurant;

    @Column(name = "max_people_by_table")
    protected int maxPeopleByTable;

    protected boolean online;

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

    public String getEmailForUs() {
        return emailForUs;
    }

    public void setEmailForUs(String emailForUs) {
        this.emailForUs = emailForUs;
    }

    public String getPhoneForClients() {
        return phoneForClients;
    }

    public void setPhoneForClients(String phoneForClients) {
        this.phoneForClients = phoneForClients;
    }

    public String getPhoneForUs() {
        return phoneForUs;
    }

    public void setPhoneForUs(String phoneForUs) {
        this.phoneForUs = phoneForUs;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isPusblished() {
        return pusblished;
    }

    public void setPusblished(boolean pusblished) {
        this.pusblished = pusblished;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }

    public String getServiceChargePerc() {
        return serviceChargePerc;
    }

    public void setServiceChargePerc(String serviceChargePerc) {
        this.serviceChargePerc = serviceChargePerc;
    }

    public boolean isLiveRestaurant() {
        return liveRestaurant;
    }

    public void setLiveRestaurant(boolean liveRestaurant) {
        this.liveRestaurant = liveRestaurant;
    }

    public int getMaxPeopleByTable() {
        return maxPeopleByTable;
    }

    public void setMaxPeopleByTable(int maxPeopleByTable) {
        this.maxPeopleByTable = maxPeopleByTable;
    }

    @Override
    public String toString() {
        return "RestaurantDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", locationLat=" + locationLat +
                ", locationLon=" + locationLon +
                ", emailForClients='" + emailForClients + '\'' +
                ", emailForUs='" + emailForUs + '\'' +
                ", phoneForClients='" + phoneForClients + '\'' +
                ", phoneForUs='" + phoneForUs + '\'' +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", pusblished=" + pusblished +
                ", ownerId=" + ownerId +
                ", serviceChargePerc='" + serviceChargePerc + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", stripeId='" + stripeId + '\'' +
                '}';
    }

    public ElasticSearchRestaurant toElasticSearchObject() {
        return new ElasticSearchRestaurant(this);
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

    protected class ElasticSearchRestaurant implements Serializable {
        //Todo remove useless fields for searches
        public long id;
        public String description;
        public Location location;
        public String name;
        public String address;
        public String postcode;
        public String city;
        public String country;
        public String emailForClients;
        public String phoneForClients;
        public String photoUrl;
        public boolean liveRestaurant;
        public int maxPeopleByTable;

        public ElasticSearchRestaurant(RestaurantDomain restaurant) {
            this.id = restaurant.id;
            this.description = restaurant.description;
            this.name = restaurant.name;
            this.address = restaurant.address;
            this.postcode = restaurant.postcode;
            this.city = restaurant.city;
            this.country = restaurant.country;
            this.emailForClients = restaurant.emailForClients;
            this.phoneForClients = restaurant.phoneForClients;
            this.photoUrl = restaurant.photoUrl;
            this.maxPeopleByTable = restaurant.maxPeopleByTable;
            this.liveRestaurant = restaurant.liveRestaurant;
            this.location = new Location(restaurant.locationLat, restaurant.locationLon);
        }

        private class Location {
            public BigDecimal lat;
            public BigDecimal lon;

            public Location(BigDecimal lat, BigDecimal lon) {
                this.lat = lat;
                this.lon = lon;
            }
        }
    }
}
