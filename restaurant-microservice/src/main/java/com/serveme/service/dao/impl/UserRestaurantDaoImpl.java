package com.serveme.service.dao.impl;

import com.serveme.service.dao.UserRestaurantDao;
import com.serveme.service.domain.UserRestaurantDomain;
import com.serveme.service.util.persistence.GenericRowMapperList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Repository
public class UserRestaurantDaoImpl implements UserRestaurantDao {

    Logger logger = Logger.getLogger(UserRestaurantDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public long create(UserRestaurantDomain user) {
        logger.log(Level.INFO, "\n\tPersisting new RestaurantDomain user: " + user.toString());
        String sql = "INSERT INTO RESTAURANT_USERS ( restaurant_id, name, surname, email, password, last_device_id, last_device_type, created) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";


        Object[] params = {user.getRestaurantId(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getLastDeviceId(), user.getLastDeviceType(), user.getCreated()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        long id = keyholder.getKey().longValue();
        user.setId(id);
        return id;

    }


    @Override
    public void update(UserRestaurantDomain user) {
        logger.log(Level.INFO, "\n\tUpdating new RestaurantDomain user: " + user.toString());

        invalidateSessionDevice(user.getLastDeviceId());

        String sql = "UPDATE RESTAURANT_USERS SET name=?, surname=?, last_device_id=?, last_device_type=?, updated=? " +
                " WHERE id=?";

        Object[] params = {user.getName(), user.getSurname(), user.getLastDeviceId(), user.getLastDeviceType(), user.getUpdated(), user.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The user restaurant " + user.getEmail() + " wasn't updated");
        }

    }

    private void invalidateSessionDevice(String lastDeviceId) {
        String sql = "UPDATE RESTAURANT_USERS SET last_device_id=?, last_device_type=? " +
                " WHERE last_device_id=?";

        Object[] params = {null, null, lastDeviceId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR);

        jdbcTemplate.update(factory.newPreparedStatementCreator(params));
    }

    @Override
    public void invalidateSessionDeviceByRestaurant(long restaurantId) {
        String sql = "UPDATE RESTAURANT_USERS  SET last_device_id = NULL" +
                " WHERE restaurant_id ="+restaurantId;
        jdbcTemplate.update(sql);
    }


    @Override
    public UserRestaurantDomain findById(long id) {

        logger.log(Level.INFO, "\n\tSelecting RestaurantDomain user by id: " + id);
        String sql = "SELECT * FROM RESTAURANT_USERS" +
                " WHERE id=?" +
                " LIMIT 0,1";
        List<UserRestaurantDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenericRowMapperList(UserRestaurantDomain.class));
        } catch (Exception ex) {
        }

        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;

        }
    }

    @Override
    public UserRestaurantDomain findByRestaurantId(long id) {

        logger.log(Level.INFO, "\n\tSelecting RestaurantDomain user by restaurantId: " + id);
        String sql = "SELECT * FROM RESTAURANT_USERS" +
                " WHERE restaurant_id=?" +
                " LIMIT 0,1";
        List<UserRestaurantDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenericRowMapperList(UserRestaurantDomain.class));
        } catch (Exception ex) {
        }

        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;

        }
    }

    @Override
    public List<UserRestaurantDomain> findAdminUsers() {
        logger.log(Level.INFO, "\n\tSelecting all RestaurantDomain");
        String sql = "SELECT * FROM RESTAURANT_USERS where account_type=1";
        List<UserRestaurantDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{}, new GenericRowMapperList(UserRestaurantDomain.class));
        } catch (Exception ex) {
        }

        if (userList != null && userList.size() > 0) {
            return userList;
        } else {
            return null;
        }
    }

    @Override
    public void removeById(long id) {
        logger.log(Level.INFO, "\n\tRemoving RestaurantDomain user id: " + id);
        String sql = "DELETE FROM  RESTAURANT_USERS WHERE id=?";

        Object[] params = {id};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The user restaurant with id" + id + " wasn't deleted");
        }

    }


    @Override
    public UserRestaurantDomain findByEmail(String email) {
        logger.log(Level.INFO, "\n\tSelecting RestaurantDomain user by email: " + email);
        String sql = "SELECT * FROM RESTAURANT_USERS" +
                " WHERE email=?" +
                " LIMIT 0,1";
        List<UserRestaurantDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{email}, new GenericRowMapperList(UserRestaurantDomain.class));
        } catch (Exception ex) {
            logger.log(Level.INFO, ex.getMessage());
        }

        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }



    @Override
    public UserRestaurantDomain findByAccessToken(String accessToken) {
        String sql = "SELECT * FROM RESTAURANT_USERS ru" +
                " JOIN RESTAURANT_USER_TOKENS rut ON rut.user_id=ru.id" +
                " WHERE rut.access_token=?" +
                " LIMIT 0,1";

        List<UserRestaurantDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{accessToken}, new GenericRowMapperList(UserRestaurantDomain.class));
        } catch (Exception ex) {
        }

        if (userList != null && userList.size() > 0) {
            return userList.get(0);

        } else {
            return null;
        }
    }
}
