package com.serveme.service.dao.impl;

import com.serveme.service.dao.UserRestaurantsTokenDao;
import com.serveme.service.domain.UserRestaurantTokenDomain;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 23/10/15.
 */
@Repository
public class UserRestaurantsTokenDaoImpl implements UserRestaurantsTokenDao {

    Logger logger = Logger.getLogger(UserRestaurantsTokenDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveToken(UserRestaurantTokenDomain token) {
        logger.log(Level.INFO, "\n\tPersisting token user: " + token.toString());
        String sql = "INSERT INTO RESTAURANT_USER_TOKENS ( access_token, user_id, created, ttl, device_id) " +
                "VALUES(?, ?, ?, ?, ?)";


        Object[] params = {token.getAccessToken(), token.getUserId(), token.getCreated(), token.getTtl(), token.getDeviceId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                new int[]{Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.VARCHAR});

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("Token not inserted: " + token.toString());
        }

    }


    @Override
    public void deleteTokensByRestaurant(long restaurantId) {

        logger.log(Level.INFO, String.format("Deleting tokens for restaurant %d", restaurantId));

        String sql = "DELETE FROM RESTAURANT_USER_TOKENS" +
                " WHERE user_id IN " +
                "(SELECT id FROM RESTAURANT_USERS WHERE restaurant_id=?)";

        Object[] params = {restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER);

        jdbcTemplate.update(factory.newPreparedStatementCreator(params));
    }
}
