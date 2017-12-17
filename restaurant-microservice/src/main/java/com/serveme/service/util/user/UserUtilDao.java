package com.serveme.service.util.user;


import com.serveme.service.util.user.domain.UserSM;

/**
 * Created by Davids-iMac on 21/05/15.
 */
public interface UserUtilDao {

    UserSM findByToken(String accessToken);


}
