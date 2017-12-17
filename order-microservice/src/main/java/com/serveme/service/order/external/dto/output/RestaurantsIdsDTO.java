package com.serveme.service.order.external.dto.output;

import com.serveme.service.order.domain.DeviceInformation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DavidChains on 7/12/15.
 */
public class RestaurantsIdsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Long> restaurantIds;
    
	public List<Long> getRestaurantIds() {
		return restaurantIds;
	}
	public void setRestaurantIds(List<Long> restaurantIds) {
		this.restaurantIds = restaurantIds;
	}
}
