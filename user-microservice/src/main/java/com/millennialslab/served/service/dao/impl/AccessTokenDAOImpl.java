package com.millennialslab.served.service.dao.impl;

import com.millennialslab.served.service.dao.AccesTokenDAO;
import com.millennialslab.served.service.domain.AccessTokenDomain;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 15/11/15.
 */
@Repository
public class AccessTokenDAOImpl implements AccesTokenDAO {


    Logger logger = Logger.getLogger(UserDAOImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;


    @Override
    public void insert(AccessTokenDomain userToken) {


        logger.log(Level.INFO, "UserDAO.insert with\n"+userToken.toString());
        String sql = "INSERT INTO USER_TOKENS (access_token, user_id, created_at, ttl) VALUES(?,?,?,?)";
        Object[] params = {userToken.getAccessToken(), userToken.getUserId(), userToken.getCreatedAt(), userToken.getTtl()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                new int[]{Types.VARCHAR, Types.INTEGER, Types.TIMESTAMP, Types.BIGINT});

        if(jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <=0){
            throw new RuntimeException("DigitsAccessToken not created");
        }

    }


    @Override
    public void delete(long userId){

        logger.log(Level.INFO, "AccessTokenDao.delete with " + userId);
        String sql = "DELETE FROM USER_TOKENS WHERE user_id=?";
        Object[] params = {userId};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                new int[]{Types.INTEGER});

        jdbcTemplate.update(factory.newPreparedStatementCreator(params));

    }

}
