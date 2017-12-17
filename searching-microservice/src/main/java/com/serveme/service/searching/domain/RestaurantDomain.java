package com.serveme.service.searching.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public class RestaurantDomain implements Serializable {

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

    private List<CategoryDomain> menuCategories;

    private List<ScheduleDomain> schedules;

    private List<HolidayDomain> holidays;

    private List<DiscountDomain> discounts;

    private long minutesRemainingToLast30minBooking;

    private long minutesRemainingToLast45minBooking;

    private long minutesRemainingToLast60minBooking;

    private long minutesRemainingToClose;

    private int maxPeopleByTable;

    private static String timezone = "Europe/London";

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

    public List<CategoryDomain> getMenuCategories() {
        return menuCategories;
    }

    public void setMenuCategories(List<CategoryDomain> menuCategories) {
        this.menuCategories = menuCategories;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isLiveRestaurant() {
        return liveRestaurant;
    }

    public void setLiveRestaurant(boolean liveRestaurant) {
        this.liveRestaurant = liveRestaurant;
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

    public List<DiscountDomain> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountDomain> discounts) {
        this.discounts = discounts;
    }

    public int getMaxPeopleByTable() {
        return maxPeopleByTable;
    }

    public void setMaxPeopleByTable(int maxPeopleByTable) {
        this.maxPeopleByTable = maxPeopleByTable;
    }

    public static final Comparator<? super RestaurantDomain> comparator = new Comparator<RestaurantDomain>() {
        @Override
        public int compare(RestaurantDomain o1, RestaurantDomain o2) {
            return (o2.isOpen() ? 1 : 0) - (o1.isOpen() ? 1 : 0);
        }
    };

    public static void determinateOpenRestaurants(List<RestaurantDomain> list) {
        long time = new Date().getTime();
        //time += TimezoneUtil.timeZoneOffSet(timezone);
        for (RestaurantDomain restaurant : list) {
            restaurant.determinateOpenState(time);
        }
    }

    public void determinateOpenState(long timestamp) {
        this.setOpen(false);
        Date today = new Date(timestamp);
        for (HolidayDomain holiday : holidays) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                if (formatter.parse(holiday.getInitHour()).getTime() < today.getTime()
                        && today.getTime() < formatter.parse(holiday.getEndHour()).getTime()) {
                    this.setOpen(false);
                    return;
                }
            } catch (Exception ignored) {
                return;
            }
        }
        for (ScheduleDomain schedule : schedules) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String currentTime = formatter.format(today);
            if (schedule.getDayOfTheWeek() == today.getDay()
                    || ((schedule.getDayOfTheWeek() == 7 && today.getDay() == 0))) {
                if (schedule.getInitHour().compareTo(currentTime) < 0
                        && currentTime.compareTo(schedule.getEndHour()) < 0) {
                    this.setOpen(true);
                    return;
                }
            }
        }
    }

    public static void determinateLastBookingTimes(List<RestaurantDomain> list) {
        for (RestaurantDomain restaurant : list) {
            restaurant.determinateLastBookingTimes();
        }
    }

    public void determinateLastBookingTimes() {
        minutesRemainingToLast30minBooking = getLastBookTime(30, 30);
        minutesRemainingToLast45minBooking = getLastBookTime(45, 30);
        minutesRemainingToLast60minBooking = getLastBookTime(60, 30);
        if (minutesRemainingToLast30minBooking > 0) {
            minutesRemainingToClose = minutesRemainingToLast30minBooking + 60;
        } else {
            minutesRemainingToClose = 0;
        }
    }

    private long getLastBookTime(int bookingTimeMinutes, int timeToEatMinutes) {
        long todayTime = new Date().getTime();
        //todayTime += TimezoneUtil.timeZoneOffSet(timezone);
        Date today = new Date(todayTime);
        SimpleDateFormat completeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (HolidayDomain holiday : holidays) {
            try {
                if (completeFormatter.parse(holiday.getInitHour()).getTime() < today.getTime()
                        && today.getTime() < completeFormatter.parse(holiday.getEndHour()).getTime()) {
                    return 0;
                }
            } catch (Exception ignored) {
                return 0;
            }
        }
        for (ScheduleDomain schedule : schedules) {
            try {
                if (schedule.getDayOfTheWeek() == today.getDay()
                        || ((schedule.getDayOfTheWeek() == 7 && today.getDay() == 0))) {
                    String[] initValues = schedule.getInitHour().split(":");
                    String[] endValues = schedule.getEndHour().split(":");
                    Date initTime = new Date(today.getYear(), today.getMonth(), today.getDate(),
                            Integer.parseInt(initValues[0]), Integer.parseInt(initValues[1]));
                    Date endTime = new Date(today.getYear(), today.getMonth(), today.getDate(),
                            Integer.parseInt(endValues[0]), Integer.parseInt(endValues[1]));
                    if (initTime.compareTo(today) < 0
                            && today.compareTo(endTime) < 0) {
                        long lastBookTime;
                        lastBookTime = endTime.getTime()
                                - (bookingTimeMinutes * 60000)
                                - (bookingTimeMinutes * timeToEatMinutes);
                        if (today.getTime() < lastBookTime) {
                            return ((lastBookTime - today.getTime()) / 1000) / 60;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        return 0;
    }
}
