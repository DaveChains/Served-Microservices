package com.millennialslab.served.service.dao.impl;

import com.millennialslab.served.service.dao.UserDAO;
import com.millennialslab.served.service.domain.UserDomain;
import com.millennialslab.served.service.util.persistence.GenericRowMapperList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.sql.Types;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 22/10/15.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    Logger logger = Logger.getLogger(UserDAOImpl.class.getName());

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDomain findByDigitsId(String digitsId) {

        try {

            logger.log(Level.INFO, "UserDAO.findByDigts with\n" + digitsId);
            List<UserDomain> userList = (List) jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE digits_id = ? LIMIT 0,1", new String[]{digitsId}, new GenericRowMapperList(UserDomain.class));
            if (userList != null && userList.size() > 0)
                return userList.get(0);
            return null;

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return null;
        }
    }
    
    @Override
    public UserDomain findById(long id) {

        try {

            List<UserDomain> userList = (List) jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE id = ? LIMIT 0,1",
                    new Long[]{id}, new GenericRowMapperList(UserDomain.class));
            if (userList != null && userList.size() > 0)
                return userList.get(0);
            return null;

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return null;
        }
    }

    @Override
    public UserDomain findByToken(String accessToken) {

        String sql = "SELECT u.*" +
                " FROM USERS u" +
                " JOIN USER_TOKENS ut ON ut.user_id=u.id" +
                " WHERE ut.access_token=?" +
                " LIMIT 0,1";
        try {

            logger.log(Level.INFO, "UserDAO.findByToken with\n" + accessToken);
            List<UserDomain> userList = (List) jdbcTemplate.queryForObject(sql, new String[]{accessToken}, new GenericRowMapperList(UserDomain.class));
            if (userList != null && userList.size() > 0)
                return userList.get(0);
            return null;

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return null;
        }

    }


    @Override
    public List<UserDomain> findByIds(List<Long> ids) {

        Map<String, List> param = Collections.singletonMap("ids", ids);


        String sql = "SELECT *" +
                " FROM USERS " +
                " WHERE id IN (:ids)" +
                " LIMIT 0,100";
        try {

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
            logger.log(Level.INFO, "UserDAO.findByIds with\n" + ids.toString());
            List<List<UserDomain>> userList = namedParameterJdbcTemplate.query(sql, param, new GenericRowMapperList(UserDomain.class));
            return userList != null && userList.size() > 0 ? userList.get(0) : null;

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return null;
        }

//        MapSqlParameterSource parameters = new MapSqlParameterSource();
//        parameters.addValue("ids", ids);
//
//
//        String sql = "SELECT *" +
//                " FROM USERS " +
//                " WHERE id IN (:ids)" +
//                " LIMIT 0,100";
//        try{
//
//            logger.log(Level.INFO, "UserDAO.findByIds with\n"+ids.toString());
//            return  (List)jdbcTemplate.query(sql,  new GenericRowMapperList(UserDomain.class), parameters);
//
//        }catch(Exception ex){
//            logger.log(Level.WARNING, ex.getMessage());
//            return null;
//        }

    }


    @Override
    public List<UserDomain> findByPhone(List<String> phones) {

        Map<String, List> param = Collections.singletonMap("phones", phones);


        String sql = "SELECT *" +
                " FROM USERS " +
                " WHERE phone_number IN (:phones)" +
                " LIMIT 0,100";
        try {

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
            logger.log(Level.INFO, "UserDAO.findByPhone with\n" + phones.toString());
            List<List<UserDomain>> userList = namedParameterJdbcTemplate.query(sql, param, new GenericRowMapperList(UserDomain.class));
            return userList != null && userList.size() > 0 ? userList.get(0) : null;

        } catch (Exception ex) {
            logger.log(Level.WARNING, ex.getMessage());
            return null;
        }

    }


    @Override
    public long insert(UserDomain user) {

        logger.log(Level.INFO, "UserDAO.insert with\n" + user.toString());
        String sql = "INSERT INTO USERS (first_name,surname, phone_number, email, digits_id, last_device_id,last_device_type, promo_code) VALUES(?,?, ?,?,?,?,?,?)";
        Object[] params = {
                user.getFirstName(),
                user.getSurname(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getDigitsId(),
                user.getLastDeviceId(),
                user.getLastDeviceType(),
                user.getPromoCode()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        return keyholder.getKey().longValue();
    }

    @Override
    public boolean update(UserDomain user) {


        logger.log(Level.INFO, "UserDAO.update with\n" + user.toString());
        String sql = "UPDATE USERS SET " +
                " first_name=?," +
                " surname=?," +
                " phone_number=?," +
                " email=?," +
                " digits_id=?," +
                " last_device_id=?," +
                " last_device_type=?," +
                " promo_code=?" +
                " WHERE id=?";
        Object[] params = new Object[]{user.getFirstName(), user.getSurname(), user.getPhoneNumber(), user.getEmail(), user.getDigitsId(), user.getLastDeviceId(), user.getLastDeviceType(), user.getPromoCode(), user.getId()};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER});

        return jdbcTemplate.update(factory.newPreparedStatementCreator(params)) <= 0;
    }

    @Override
    public long insertWebUser(String firstName, String lastName, String email) {
        logger.log(Level.INFO, "Executing UserDAO.insertWebUser(" + firstName+","+lastName+","+email+")");
        String sql = "INSERT INTO WEB_USERS (first_name, surname, email) VALUES(?,?,?)";
        Object[] params = {
                firstName,
                lastName,
                email};

        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(sql,
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});

        factory.setReturnGeneratedKeys(true);
        KeyHolder keyholder = new GeneratedKeyHolder();

        jdbcTemplate.update(factory.newPreparedStatementCreator(params), keyholder);
        return keyholder.getKey().longValue();
    }
}
