package com.serveme.service.domain;

/**
 * Created by Edgar on 7/15/2016.
 */
public class RestaurantDistanceData {


    private double minDistance;
    private double distance;
    private boolean valid;

    public RestaurantDistanceData(double distance, double minDistance, boolean valid) {
        this.distance = distance;
        this.valid = valid;
        this.minDistance = minDistance;
    }

    public static RestaurantDistanceData getTestObject() {
        return new RestaurantDistanceData(0, 0, true);
    }


    public double getDistance() {
        return distance;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public boolean isValid() {
        return valid;
    }


    @Override
    public String toString() {
        return "RestaurantDistanceData{" +
                "distance=" + distance +
                ", minDistance=" + minDistance +
                ", valid=" + valid +
                '}';
    }
}
