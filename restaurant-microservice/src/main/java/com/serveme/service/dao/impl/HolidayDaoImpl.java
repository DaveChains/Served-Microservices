package com.serveme.service.dao.impl;

import com.serveme.service.dao.HolidayDao;
import com.serveme.service.domain.HolidayDomain;
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
public class HolidayDaoImpl implements HolidayDao {

    Logger logger = Logger.getLogger(HolidayDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public long create(HolidayDomain holiday) {
        logger.log(Level.INFO, "\n\tCreating new holiday. Name: ");
        String sql = "INSERT INTO HOLIDAYS (restaurant_id, init_hour, end_hour) " +
                "VALUES(?, ?, ?)";


        Object[] params = {holiday.getRestaurantId(), holiday.getInitHour(), holiday.getEndHour() + " 23:59:59"};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        return keyholder.getKey().longValue();
    }

    @Override
    public List<HolidayDomain> getByRestaurantId(long restaurantId) {
        logger.log(Level.INFO, "\n\tSelecting holidays by restaurant id: " + restaurantId);
        String sql = "SELECT * FROM HOLIDAYS" +
                " WHERE restaurant_id=?";

        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId}, new GenericRowMapperList(HolidayDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public long update(List<HolidayDomain> holidays, long restaurantId) {
        int success = 0;
        for (HolidayDomain holiday : holidays) {
            holiday.setRestaurantId(restaurantId);
            if (holiday.getId() != 0) {
                update(holiday);
            } else {
                create(holiday);
            }
            success++;
        }
        return success;
    }

    public long update(HolidayDomain holiday) {
        logger.log(Level.INFO, "\n\tUpdating holiday. Day: " + holiday.getId());
        String sql = "UPDATE HOLIDAYS SET restaurant_id=?, init_hour=?, end_hour=? " +
                " WHERE id=?";

        Object[] params = {holiday.getRestaurantId(), holiday.getInitHour(), holiday.getEndHour() + " 23:59:59", holiday.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The holiday " + holiday.getId() + " for restaurant " + holiday.getRestaurantId() + " wasn't updated");
        }

        return holiday.getId();
    }

    @Override
    public void removeById(long id, long restaurantId) {
        logger.log(Level.INFO, "\n\tRemoving holiday id: " + id);
        String sql = "DELETE FROM HOLIDAYS WHERE id=? AND restaurant_id=?";

        Object[] params = {id, restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The holiday with id" + id + " wasn't deleted");
        }

    }
}
