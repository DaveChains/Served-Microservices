package com.serveme.service.builder;

import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.domain.UserRestaurantTokenDomain;
import com.serveme.service.util.StringUtil;

import java.sql.Timestamp;

/**
 * Created by Davids-iMac on 23/10/15.
 */
public class TokenBuilder {

    private final static long TTL = 3600000;

    private long userId;

    private String deviceId;

    private String username;

    private long restaurantId;

    public TokenBuilder(){}


    public TokenBuilder setUserRestaurant(UserRestaurantDomain userRestaurantDomain){
        this.userId     = userRestaurantDomain.getId();
        this.restaurantId = userRestaurantDomain.getRestaurantId();
        this.deviceId   = userRestaurantDomain.getLastDeviceId();
        this.username = userRestaurantDomain.getName();
        return this;
    }

    public UserRestaurantTokenDomain build(){
        UserRestaurantTokenDomain userRestaurantTokenDomain = new UserRestaurantTokenDomain();
        userRestaurantTokenDomain.setUserId(this.userId);
        userRestaurantTokenDomain.setRestaurantId(this.restaurantId);
        userRestaurantTokenDomain.setAccessToken(StringUtil.generateAccessToken());
        userRestaurantTokenDomain.setTtl(TTL);
        userRestaurantTokenDomain.setDeviceId(deviceId);
        userRestaurantTokenDomain.setCreated(new Timestamp(System.currentTimeMillis()));
        userRestaurantTokenDomain.setUsername(this.username);
        return userRestaurantTokenDomain;
    }


    public UserRestaurantTokenDomain buildAdminToken(String adminToken, long adminRestaurantId) {
        UserRestaurantTokenDomain userRestaurantTokenDomain = new UserRestaurantTokenDomain();
        userRestaurantTokenDomain.setAccessToken(adminToken);
        userRestaurantTokenDomain.setUserId(adminRestaurantId);
        userRestaurantTokenDomain.setRestaurantId(this.restaurantId);
        userRestaurantTokenDomain.setTtl(TTL);
        userRestaurantTokenDomain.setCreated(new Timestamp(System.currentTimeMillis()));
        userRestaurantTokenDomain.setUsername("Admin");
        return userRestaurantTokenDomain;
    }
}
