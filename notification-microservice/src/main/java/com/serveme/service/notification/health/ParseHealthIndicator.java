package com.serveme.service.notification.health;

import com.serveme.service.notification.external.service.ParseService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Edgar on 6/1/2016.
 */
@Component
public class ParseHealthIndicator implements HealthIndicator {

    @Inject
    protected ParseService parseService;

    @Override
    public Health health() {
        boolean success = check();
        if (success) {
            return Health.up().withDetail("details", "connection established to parseService").build();
        } else {
            return Health.down().withDetail("details", "unreachable service").build();
        }
    }

    public boolean check() {
        return parseService.isReachable();
    }
}
