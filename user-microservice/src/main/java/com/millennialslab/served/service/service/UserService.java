package com.millennialslab.served.service.service;

import com.millennialslab.served.service.domain.AccessTokenDomain;
import com.millennialslab.served.service.api.dto.input.UserUpdateDetailInputDTO;
import com.millennialslab.served.service.domain.UserDomain;

import java.util.List;

/**
 * Created by Davids-iMac on 06/11/15.
 */
public interface UserService {

    String[] getUserHiddenFields();

    UserDomain authenticateWithDigits(String serviceProvider, String authorizationHeader,String deviceType,  String deviceId);

    UserDomain getByToken(String accessToken);

    boolean updateUser(UserDomain user);

    AccessTokenDomain createAccessToken(long userId);

    List<UserDomain> getUserByIds(List<Long> ids);

    List<UserDomain> getUserByPhone(List<String> phones);

    long createUserFromWebPanel(UserUpdateDetailInputDTO userDomain);

    UserDomain getById(long id);
}
