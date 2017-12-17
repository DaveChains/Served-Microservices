package com.serveme.service.util.user;

import com.serveme.service.util.exception.http.AuthenticationException;
import com.serveme.service.util.user.domain.UserSM;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by Davids-iMac on 19/06/15.
 */
@Component
public class UserUtility {

    @Inject
    private UserUtilDao userUtilDao;


    public UserSM authenticateUser(String accessToken){
        UserSM userSM = userUtilDao.findByToken(accessToken);
        if(userSM != null){
            return userSM;
        }else{
            throw new AuthenticationException(accessToken + " is not a valid token", "authentication fail");
        }
    }


}
