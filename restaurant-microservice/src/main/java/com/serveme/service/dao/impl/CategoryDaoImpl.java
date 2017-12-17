package com.serveme.service.dao.impl;

import com.serveme.service.dao.CategoryDao;
import com.serveme.service.domain.CategoryDomain;
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
public class CategoryDaoImpl implements CategoryDao {

    Logger logger = Logger.getLogger(CategoryDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public long create(CategoryDomain category) {
        logger.log(Level.INFO, "\n\tCreating new Category. Name: " + category.getName());
        String sql = "INSERT INTO CATEGORIES(restaurant_id, name, position) " +
                "VALUES(?, ?, ?)";


        Object[] params = {category.getRestaurantId(), category.getName(), category.getPriority()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.VARCHAR, Types.INTEGER);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        long id = keyholder.getKey().longValue();
        return id;

    }

    @Override
    public void update(CategoryDomain category) {
        logger.log(Level.INFO, "\n\tCreating new category. Name: " + category.getName());
        String sql = "UPDATE CATEGORIES SET restaurant_id=?, name=?, position=? " +
                " WHERE id=?";


        Object[] params = {category.getRestaurantId(), category.getName(), category.getPriority(), category.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.VARCHAR, Types.INTEGER, Types.INTEGER);


        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The category " + category.getName() + " for restaurant " + category.getRestaurantId() + " wasn't updated");
        }

    }

    @Override
    public CategoryDomain findById(long id) {

        logger.log(Level.INFO, "\n\tSelecting category by id: " + id);
        String sql = "SELECT * FROM CATEGORIES" +
                " WHERE id=?" +
                " LIMIT 0,1";
        List<CategoryDomain> userList = null;
        try {
            userList = (List) jdbcTemplate.queryForObject(sql, new Object[]{id}, new GenericRowMapperList(CategoryDomain.class));
        } catch (Exception ex) {
        }

        if (userList != null && userList.size() > 0) {
            return userList.get(0);

        } else {
            return null;

        }
    }

    @Override
    public List<CategoryDomain> findByRestaurant(long restaurantId) {

        logger.log(Level.INFO, "\n\tSelecting Category by restaurant id: " + restaurantId);
        String sql = "SELECT * FROM CATEGORIES" +
                " WHERE restaurant_id=?" +
                " ORDER BY position DESC" +
                " LIMIT 0,1000";

        List<CategoryDomain> userList = null;
        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId}, new GenericRowMapperList(CategoryDomain.class));

        } catch (Exception ex) {
            return new ArrayList<>();

        }


    }


    @Override
    public void removeById(long restaurantId, long id) {
        logger.log(Level.INFO, "\n\tRemoving category id: " + id);

        removeDishesByCategory(restaurantId, id);

        String sql = "DELETE FROM CATEGORIES WHERE id=? AND restaurant_id=?";

        Object[] params = {id, restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The category with id" + id + " wasn't deleted");
        }

    }

    private void removeDishesByCategory(long restaurantId, long categoryId) {

        logger.log(Level.INFO, "\n\tRemoving dishes from category id: " + categoryId);

        String sql = "DELETE FROM DISHES WHERE category_id=? AND restaurant_id=?";

        Object[] params = {categoryId, restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER);

        jdbcTemplate.update(factory.newPreparedStatementCreator(params));
    }

}
