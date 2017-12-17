package com.serveme.service.order.health;

import com.serveme.service.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Edgar on 6/1/2016.
 */
@Component
public class MongoHealthIndicator implements HealthIndicator {

    @Inject
    protected OrderService orderService;

    @Value("${spring.enums.mongodb.host}")
    protected String mongoHost;

    @Override
    public Health health() {
        Object errorCode = check();
        if (errorCode instanceof Exception) {
            return Health.down().withException((Exception) errorCode).build();
        }
        return Health.up().withDetail("details", "connection established to " + mongoHost).build();
    }

    public Object check() {
        try {
            orderService.getAllHistory(0, 10);
            return 0;
        } catch (Exception e) {
            return e;
        }
    }
}
