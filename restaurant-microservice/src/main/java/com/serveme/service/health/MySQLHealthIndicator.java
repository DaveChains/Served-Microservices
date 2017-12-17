package com.serveme.service.health;

import com.serveme.service.domain.RestaurantDomain;
import com.serveme.service.service.RestaurantService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Edgar on 6/1/2016.
 */
@Component
public class MySQLHealthIndicator implements HealthIndicator {

    @Inject
    protected RestaurantService restaurantService;

    @Value("${spring.datasource.url}")
    protected String mySQLHost;

    @Override
    public Health health() {
        Object response = check();
        if (response instanceof Exception) {
            return Health.down().withException((Exception) response).build();
        }
        return Health.up()
                .withDetail("storedRestaurants", response)
                .withDetail("details", "connection established to " + mySQLHost).build();
    }

    public Object check() {
        try {
            List<RestaurantDomain> restaurants = restaurantService.getAllRestaurants();
            return restaurants.size();
        } catch (Exception e) {
            return e;
        }
    }
}
