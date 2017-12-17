package com.serveme.service.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DavidChains on 24/04/2016.
 */
public class SearchObject implements Serializable {
    private Location location;
    private String termsIn = "";

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setTermsIn(String termsIn) {
        this.termsIn = termsIn;
    }

    public String getTermsIn() {
        return termsIn;
    }
}
