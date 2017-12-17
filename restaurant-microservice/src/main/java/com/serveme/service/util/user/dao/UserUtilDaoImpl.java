package com.serveme.service.util.user.dao;

import com.serveme.service.util.persistence.GenericRowMapperList;
import com.serveme.service.util.user.UserUtilDao;
import com.serveme.service.util.user.domain.UserSM;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Davids-iMac on 06/06/15.
 */
@Repository
@SuppressWarnings("unused")
public class UserUtilDaoImpl implements UserUtilDao {

    @Inject
    private JdbcTemplate jdbcTemplate;


    @Override
    public UserSM findByToken(String accessToken) {

        try{
            String sql = "SELECT u.* FROM USERS u " +
                    " JOIN USER_TOKENS ut ON u.id=ut.user_id" +
                    " WHERE ut.access_token=? LIMIT 0,1";

            List<UserSM> userList = (List)jdbcTemplate.queryForObject(sql, new String[]{accessToken}, new GenericRowMapperList(UserSM.class));
            if(userList !=null && userList.size() > 0)
                return userList.get(0);
            return null;

        }catch(Exception ex){
            return null;
        }


    }
}
