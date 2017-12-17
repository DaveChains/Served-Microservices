package com.serveme.service.dao.impl;

import com.serveme.service.dao.ExtrasDao;
import com.serveme.service.domain.ExtraDomain;
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

@Repository
public class ExtrasDaoImpl implements ExtrasDao {

    Logger logger = Logger.getLogger(ExtrasDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public int update(List<ExtraDomain> list, long restaurantId) {
        int success = 0;
        for (ExtraDomain extraDomain : list) {
            extraDomain.setRestaurantId(restaurantId);
            if (extraDomain.getId() != 0) {
                update(extraDomain);
            } else {
                create(extraDomain);
            }
            success++;
        }
        return success;
    }

    @Override
    public List<ExtraDomain> findById(long restaurantId, long dishId) {
        logger.log(Level.INFO, "\n\tSelecting extras by restaurant id: " + restaurantId);
        String sql = "SELECT * FROM EXTRAS" +
                " WHERE restaurant_id=? and dish_id=?";

        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId, dishId},
                    new GenericRowMapperList(ExtraDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ExtraDomain> findById(long restaurantId) {
        logger.log(Level.INFO, "\n\tSelecting extras by restaurant id: " + restaurantId);
        String sql = "SELECT * FROM EXTRAS" +
                " WHERE restaurant_id=?";

        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId},
                    new GenericRowMapperList(ExtraDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public long create(ExtraDomain extraDomain) {
        logger.log(Level.INFO, "\n\tCreating new extra. Name: ");
        String sql = "INSERT INTO EXTRAS (restaurant_id, dish_id, category_id, value, price) " +
                "VALUES(?, ?, ?, ?, ?)";


        Object[] params = {extraDomain.getRestaurantId(), extraDomain.getDishId(), extraDomain.getCategoryId(),
                extraDomain.getValue(), extraDomain.getPrice()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                Types.VARCHAR, Types.DECIMAL);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        return keyholder.getKey().longValue();
    }

    public long update(ExtraDomain extraDomain) {
        logger.log(Level.INFO, "\n\tUpdating extra. Day: " + extraDomain.getId());
        String sql = "UPDATE EXTRAS SET restaurant_id=?, dish_id=?, category_id=?, value=?, price=? " +
                " WHERE id=?";

        Object[] params = {extraDomain.getRestaurantId(), extraDomain.getDishId(), extraDomain.getCategoryId(),
                extraDomain.getValue(), extraDomain.getPrice(), extraDomain.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.INTEGER,
                Types.VARCHAR, Types.DECIMAL, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The extra " + extraDomain.getId() + " for restaurant " + extraDomain.getRestaurantId() + " wasn't updated");
        }

        return extraDomain.getId();
    }

    @Override
    public void removeById(long id, long restaurantId) {
        logger.log(Level.INFO, "\n\tRemoving extra id: " + id);
        String sql = "DELETE FROM EXTRAS WHERE id=? AND restaurant_id=?";

        Object[] params = {id, restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The extra with id" + id + " wasn't deleted");
        }

    }
}
