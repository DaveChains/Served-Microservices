package com.serveme.service.order.api.dto.input;

import com.serveme.service.order.domain.Location;

/**
 * Created by Edgar on 7/15/2016.
 */
public class NewCreationOrderInputDTO extends CreationOrderInputDTO {
    protected Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
