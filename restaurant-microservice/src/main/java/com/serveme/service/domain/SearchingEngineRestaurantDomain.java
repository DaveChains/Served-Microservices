package com.serveme.service.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class SearchingEngineRestaurantDomain implements Serializable {

    private long id;

    private String name;

    private String description;

    private String address;

    private String postcode;

    private String city;

    private String country;

    private String emailForClients;

    private String phoneForClients;

    private String photoUrl;

    private Location location;

    private boolean liveRestaurant;

    private boolean active = true;

    private boolean open;

    private boolean online;

    private List<CategoryDomain> menuCategories;

    private List<ScheduleDomain> schedules;

    private List<HolidayDomain> holidays;

    private List<DiscountDomain> discounts;

    private long minutesRemainingToLast30minBooking;

    private long minutesRemainingToLast45minBooking;

    private long minutesRemainingToLast60minBooking;

    private long minutesRemainingToClose;

    private int numberOfOrders;

    private int maxPeopleByTable;

    private BigDecimal discount;

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isLiveRestaurant() {
        return liveRestaurant;
    }

    public void setLiveRestaurant(boolean liveRestaurant) {
        this.liveRestaurant = liveRestaurant;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<CategoryDomain> getMenuCategories() {
        return menuCategories;
    }

    public void setMenuCategories(List<CategoryDomain> menuCategories) {
        this.menuCategories = menuCategories;
    }

    public List<ScheduleDomain> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDomain> schedules) {
        this.schedules = schedules;
    }

    public List<HolidayDomain> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<HolidayDomain> holidays) {
        this.holidays = holidays;
    }

    public List<DiscountDomain> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDomain> discounts) {
        this.discounts = discounts;
    }

    public long getMinutesRemainingToLast30minBooking() {
        return minutesRemainingToLast30minBooking;
    }

    public void setMinutesRemainingToLast30minBooking(long minutesRemainingToLast30minBooking) {
        this.minutesRemainingToLast30minBooking = minutesRemainingToLast30minBooking;
    }

    public long getMinutesRemainingToLast45minBooking() {
        return minutesRemainingToLast45minBooking;
    }

    public void setMinutesRemainingToLast45minBooking(long minutesRemainingToLast45minBooking) {
        this.minutesRemainingToLast45minBooking = minutesRemainingToLast45minBooking;
    }

    public long getMinutesRemainingToLast60minBooking() {
        return minutesRemainingToLast60minBooking;
    }

    public void setMinutesRemainingToLast60minBooking(long minutesRemainingToLast60minBooking) {
        this.minutesRemainingToLast60minBooking = minutesRemainingToLast60minBooking;
    }

    public long getMinutesRemainingToClose() {
        return minutesRemainingToClose;
    }

    public void setMinutesRemainingToClose(long minutesRemainingToClose) {
        this.minutesRemainingToClose = minutesRemainingToClose;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int num) {
        this.numberOfOrders = num;
    }

    public int getMaxPeopleByTable() {
        return maxPeopleByTable;
    }

    public void setMaxPeopleByTable(int maxPeopleByTable) {
        this.maxPeopleByTable = maxPeopleByTable;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "SearchingEngineRestaurantDomain [id=" + id + ", name=" + name + ", description=" + description
                + ", address=" + address + ", postcode=" + postcode + ", city=" + city + ", country=" + country
                + ", emailForClients=" + emailForClients + ", phoneForClients=" + phoneForClients + ", photoUrl="
                + photoUrl + ", location=" + location + ", liveRestaurant=" + liveRestaurant + ", active=" + active
                + ", open=" + open + ", menuCategories=" + menuCategories + ", schedules=" + schedules + ", holidays="
                + holidays + ", discounts=" + discounts + ", minutesRemainingToLast30minBooking="
                + minutesRemainingToLast30minBooking + ", minutesRemainingToLast45minBooking="
                + minutesRemainingToLast45minBooking + ", minutesRemainingToLast60minBooking="
                + minutesRemainingToLast60minBooking + ", minutesRemainingToClose=" + minutesRemainingToClose + "]";
    }
}
