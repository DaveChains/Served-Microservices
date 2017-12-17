package com.millennialslab.served.service.health;

import com.millennialslab.served.service.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Edgar on 6/1/2016.
 */
@Component
public class MySQLHealthIndicator implements HealthIndicator {

    @Inject
    protected UserService userService;

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
            userService.getById(33);
            return 0;
        } catch (Exception e) {
            return e;
        }
    }
}
