package com.serveme.service.service;

import com.serveme.service.domain.CategoryDomain;
import com.serveme.service.domain.DishDomain;
import com.serveme.service.domain.RestaurantDomain;

/**
 * Created by Davids-iMac on 26/10/15.
 */
public interface GeolocationService {

    Object getLocationByPostcode(String postcode);
}
