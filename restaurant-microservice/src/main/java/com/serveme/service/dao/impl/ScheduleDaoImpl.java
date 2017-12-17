package com.serveme.service.dao.impl;

import com.serveme.service.dao.ScheduleDao;
import com.serveme.service.domain.ScheduleDomain;
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
public class ScheduleDaoImpl implements ScheduleDao {

    Logger logger = Logger.getLogger(ScheduleDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    /**
     * The values are numbered following the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
     */
    public long create(ScheduleDomain scheduleDomain) {
        logger.log(Level.INFO, "\n\tCreating new schedule. Restaurant: " + scheduleDomain.getRestaurantId() + " for day " + scheduleDomain.getDayOfTheWeek());
        String sql = "INSERT INTO SCHEDULES(restaurant_id, day, init_hour, end_hour) " +
                "VALUES(?, ?, ?, ?)";

        Object[] params = {scheduleDomain.getRestaurantId(), scheduleDomain.getDayOfTheWeek(), scheduleDomain.getInitHour() + ":00", scheduleDomain.getEndHour() + ":00"};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.TIME, Types.TIME);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        return keyholder.getKey().longValue();
    }

    @Override
    public int update(List<ScheduleDomain> scheduleDomainList, long restaurantId) {
        int success = 0;
        for (ScheduleDomain scheduleDomain : scheduleDomainList) {
            scheduleDomain.setRestaurantId(restaurantId);
            if (scheduleDomain.getId() == 0) {
                if (scheduleDomain.getDayOfTheWeek() != 99) {
                    create(scheduleDomain);
                } else {
                    for (int i = 1; i < 8; i++) {
                        scheduleDomain.setDayOfTheWeek(i);
                        create(scheduleDomain);
                    }
                }
                continue;
            }

            logger.log(Level.INFO, "\n\tUpdating schedule. Day: " + scheduleDomain.getDayOfTheWeek());
            String sql = "UPDATE SCHEDULES SET restaurant_id=?, day=?, init_hour=?, end_hour=? " +
                    " WHERE id=?";

            Object[] params = {scheduleDomain.getRestaurantId(), scheduleDomain.getDayOfTheWeek(), scheduleDomain.getInitHour() + ":00", scheduleDomain.getEndHour() + ":00", scheduleDomain.getId()};

            PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.TIME, Types.TIME, Types.INTEGER);

            if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
                throw new RuntimeException("The schedule for day " + scheduleDomain.getDayOfTheWeek() + " for restaurant " + scheduleDomain.getRestaurantId() + " wasn't updated");
            }
            success++;
        }
        return success;
    }

    @Override
    public List<ScheduleDomain> getByRestaurantId(long restaurantId) {
        logger.log(Level.INFO, "\n\tSelecting schedules by restaurant id: " + restaurantId);
        String sql = "SELECT * FROM SCHEDULES" +
                " WHERE restaurant_id=?" +
                " order by day LIMIT 0,1000";

        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId}, new GenericRowMapperList(ScheduleDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(long id, long restaurantId) {
        logger.log(Level.INFO, "\n\tDeleting schedules by restaurant id: " + restaurantId);
        String sql = "DELETE FROM SCHEDULES WHERE id=? AND restaurant_id=?";

        Object[] params = {id, restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The schedule with id" + id + " wasn't deleted");
        }
    }
}
