package com.serveme.service.dao.impl;

import com.serveme.service.dao.FoodOptionDao;
import com.serveme.service.domain.FoodOptionDomain;
import com.serveme.service.util.persistence.GenericRowMapperList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Repository
public class FoodOptionDaoImpl implements FoodOptionDao {

    Logger logger = Logger.getLogger(FoodOptionDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public long create(FoodOptionDomain foodOption) {
        logger.log(Level.INFO, "\n\tCreating new Food option. Name: " + foodOption.getValue());
        String sql = "INSERT INTO FOOD_OPTIONS(dish_id, value, restaurant_id) " +
                "VALUES(?, ?, ?)";

        Object[] params = {foodOption.getDishId(), foodOption.getValue(), foodOption.getRestaurantId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.VARCHAR, Types.INTEGER);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        long id = keyholder.getKey().longValue();
        return id;
    }

    @Override
    public long update(FoodOptionDomain foodOption) {
        logger.log(Level.INFO, "\n\tUpdating food option. Name: " + foodOption.getValue());
        String sql = "UPDATE FOOD_OPTIONS SET dish_id=?, value=?, status=?, restaurant_id=? " +
                " WHERE id=?";

        Object[] params = {foodOption.getDishId(), foodOption.getValue(), foodOption.getStatus(), foodOption.getRestaurantId(), foodOption.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The food option " + foodOption.getValue() + " for dish " + foodOption.getDishId() + " wasn't updated");
        }

        return foodOption.getId();
    }

    @Override
    public FoodOptionDomain findById(long id) {
        logger.log(Level.INFO, "\n\tSelecting category by id: " + id);
        String sql = "SELECT * FROM FOOD_OPTIONS" +
                " WHERE id=?" +
                " LIMIT 0,1";
        List<FoodOptionDomain> list = null;
        try {
            list = (List) jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenericRowMapperList(FoodOptionDomain.class));
        } catch (Exception ex) {
        }

        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<FoodOptionDomain> findByDish(long dishId) {
        logger.log(Level.INFO, "\n\tSelecting Food options by dish id: " + dishId);
        String sql = "SELECT * FROM FOOD_OPTIONS" +
                " WHERE dish_id=?" +
                " LIMIT 0,1000";

        List<FoodOptionDomain> userList = null;
        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{dishId}, new GenericRowMapperList(FoodOptionDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<FoodOptionDomain> findByRestaurant(long restaurantId) {
        logger.log(Level.INFO, "\n\tSelecting Food options by restaurant id: " + restaurantId);
        String sql = "SELECT * FROM FOOD_OPTIONS" +
                " WHERE restaurant_id=?" +
                " LIMIT 0,1000";

        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId}, new GenericRowMapperList(FoodOptionDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void removeById(long id) {
        logger.log(Level.INFO, "\n\tRemoving food option id: " + id);
        String sql = "DELETE FROM FOOD_OPTIONS WHERE id=?";

        Object[] params = {id};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The food option with id" + id + " wasn't deleted");
        }
    }

    @Override
    public void removeAllByDish(long dishId) {
        logger.log(Level.INFO, "\n\tRemoving food option from dish id: " + dishId);
        String sql = "DELETE FROM FOOD_OPTIONS WHERE dish_id=?";

        Object[] params = {dishId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            logger.log(Level.INFO, "The food option with dish id" + dishId + " wasn't deleted");
        }
    }
}
