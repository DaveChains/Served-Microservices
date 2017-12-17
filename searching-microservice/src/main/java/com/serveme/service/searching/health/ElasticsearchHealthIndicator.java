package com.serveme.service.searching.health;

import com.serveme.service.searching.domain.Location;
import com.serveme.service.searching.domain.RestaurantDomain;
import com.serveme.service.searching.domain.SearchObject;
import com.serveme.service.searching.service.RestaurantService;
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
public class ElasticsearchHealthIndicator implements HealthIndicator {

    @Inject
    protected RestaurantService restaurantService;

    @Value("${spring.data.elasticsearch.host}")
    protected String elasticsearchHost;

    @Override
    public Health health() {
        Object response = check();
        if (response instanceof Exception) {
            return Health.down().withException((Exception) response).build();
        }

        System.out.println("**************************************************************************************elasticccc services failed   "
                + elasticsearchHost.toString() );

        return Health.up()
                .withDetail("indexedRestaurants", response)
                .withDetail("details", "connection established to " + elasticsearchHost).build();
    }

    public Object check() {
        try {
            SearchObject searchObject = new SearchObject();
            Location location = new Location();
            searchObject.setLocation(location);
            searchObject.setShowTestRestaurants(true);
            //TODO connection fix, make research about locations colecction.
                        List<RestaurantDomain> restaurants = restaurantService.findRestaurants(searchObject);
            return restaurants.size();
//            return 1;
        } catch (Exception e) {
            System.out.println("restaurant services failed");
            return e;
        }
    }
}
