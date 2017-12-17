package com.serveme.service.dao.impl;

import com.serveme.service.dao.DiscountDao;
import com.serveme.service.domain.DiscountDomain;
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
public class DiscountDaoImpl implements DiscountDao {

    Logger logger = Logger.getLogger(DiscountDaoImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    public long create(DiscountDomain discountDomain) {
        logger.log(Level.INFO, "\n\tCreating new discount. Restaurant: " + discountDomain.getRestaurantId());
        String sql = "INSERT INTO DISCOUNTS (restaurant_id, init_order, end_order, init_date, end_date, percentage, flat_amount, days_count) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        Object[] params = {discountDomain.getRestaurantId(), discountDomain.getInitOrder(), discountDomain.getEndOrder(),
                discountDomain.getInitDate(), discountDomain.getEndDate(), discountDomain.getPercentage(), discountDomain.getFlatAmount(), discountDomain.getDaysCount()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER,
                Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER, Types.INTEGER);

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        return keyholder.getKey().longValue();
    }

    @Override
    public int update(List<DiscountDomain> discountDomainList, long restaurantId) {
        int success = 0;
        for (DiscountDomain discountDomain : discountDomainList) {
            discountDomain.setRestaurantId(restaurantId);
            if (discountDomain.getId() == 0) {
                create(discountDomain);
                continue;
            }

            logger.log(Level.INFO, "\n\tUpdating discount for restaurant " + discountDomain.getRestaurantId());
            String sql = "UPDATE DISCOUNTS SET restaurant_id=?, init_order=?, end_order=?, init_date=?, end_date=?, percentage=?, flat_amount=?, days_count=? " +
                    " WHERE id=?";

            Object[] params = {discountDomain.getRestaurantId(), discountDomain.getInitOrder(), discountDomain.getEndOrder(),
                    discountDomain.getInitDate(), discountDomain.getEndDate(), discountDomain.getPercentage(), discountDomain.getFlatAmount(), discountDomain.getDaysCount(), discountDomain.getId()};

            PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER);

            if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
                throw new RuntimeException("The discount " + discountDomain.getId() + " for restaurant " + discountDomain.getRestaurantId() + " wasn't updated");
            }
            success++;
        }
        return success;
    }

    @Override
    public List<DiscountDomain> getByRestaurantId(long restaurantId) {
        logger.log(Level.INFO, "\n\tSelecting schedules by restaurant id: " + restaurantId);
        String sql = "SELECT * FROM DISCOUNTS" +
                " WHERE restaurant_id=?" +
                " order by init_order LIMIT 0,1000";

        try {
            return (List) jdbcTemplate.queryForObject(sql, new Object[]{restaurantId}, new GenericRowMapperList(DiscountDomain.class));
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(long id, long restaurantId) {
        logger.log(Level.INFO, "\n\tDeleting discounts by restaurant id: " + restaurantId);
        String sql = "DELETE FROM DISCOUNTS WHERE id=? AND restaurant_id=?";

        Object[] params = {id, restaurantId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, Types.INTEGER, Types.INTEGER);

        if (jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0) {
            throw new RuntimeException("The discount with id" + id + " wasn't deleted");
        }
    }
}
