package com.serveme.service.order.domain;

/**
 * Created by Edgar on 7/15/2016.
 */
public class RestaurantDistanceData {

    private double distance;
    private boolean valid;

    public RestaurantDistanceData(double distance, boolean valid) {
        this.distance = distance;
        this.valid = valid;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
