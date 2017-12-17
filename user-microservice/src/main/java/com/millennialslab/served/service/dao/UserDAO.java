package com.millennialslab.served.service.dao;

import com.millennialslab.served.service.domain.UserDomain;

import java.util.List;

/**
 * Created by Davids-iMac on 25/10/15.
 */
public interface UserDAO {

    UserDomain findByDigitsId(String digitsId);

    UserDomain findByToken(String accessToken);

    List<UserDomain> findByIds(List<Long> ids);

    List<UserDomain> findByPhone(List<String> phones);

    long insert(UserDomain user);

    boolean update(UserDomain user);

    long insertWebUser(String name, String surname, String email);
    
    UserDomain findById(long id);
}
