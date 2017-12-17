package com.serveme.service.dao.impl;

import com.serveme.service.dao.DishDao;
import com.serveme.service.domain.DishDomain;
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
public class DishDaoImpl implements DishDao {

    Logger logger = Logger.getLogger(DishDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public long create(DishDomain dish) {
        logger.log(Level.INFO, "\n\tCreating new dish. Name: " + dish.getName());
        String sql = "INSERT INTO DISHES(restaurant_id, category_id, name, description, price, position) " +
                "VALUES(?, ?, ?, ?, ?, ?)";


        Object[] params = {dish.getRestaurantId(), dish.getCategoryId(), dish.getName(), dish.getDescription(), dish.getPrice(), dish.getPriority()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.INTEGER);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        long id = keyholder.getKey().longValue();
        return id;
    }

    @Override
    public void update(DishDomain dish) {
        logger.log(Level.INFO, "\n\tCreating new dish. Name: " + dish.getName());
        String sql = "UPDATE DISHES SET restaurant_id=?, category_id=?, name=?, description=?, price=?, position=?, runout=? " +
                " WHERE id=?";


        Object[] params = {dish.getRestaurantId(), dish.getCategoryId(), dish.getName(), dish.getDescription(), dish.getPrice(), dish.getPriority(), dish.getRunout(), dish.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.INTEGER, Types.DATE, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The dish " + dish.getName() + " for restaurant " + dish.getRestaurantId() + " and category " + dish.getCategoryId() + " wasn't updated");
        }
    }

    @Override
    public DishDomain findById(long id) {

        logger.log(Level.INFO, "\n\tSelecting Dish by id: " + id);
        String sql = "SELECT * FROM DISHES" +
                " WHERE id=?" +
                " LIMIT 0,1";
        List<DishDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenericRowMapperList(DishDomain.class));
        } catch (Exception ex) {
        }

        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<DishDomain> findByRestaurantAndCategory(long restaurantId, long categoryId) {

        logger.log(Level.INFO, "\n\tSelecting Dish by category id: " + categoryId);
        String sql = "SELECT * FROM DISHES" +
                " WHERE category_id=? AND restaurant_id=?" +
                " ORDER BY position DESC" +
                " LIMIT 0,1000";

        List<DishDomain> userList = null;
        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{categoryId, restaurantId}, new GenericRowMapperList(DishDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void removeById(long restaurantId, long id) {
        logger.log(Level.INFO, "\n\tRemoving dish id: " + id);
        String sql = "DELETE FROM  DISHES WHERE id=? AND restaurant_id=?";

        Object[] params = {id, restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The dish with id" + id + " wasn't deleted");
        }

    }

}
