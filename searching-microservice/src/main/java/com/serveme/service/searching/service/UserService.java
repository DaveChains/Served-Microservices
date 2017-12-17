package com.serveme.service.searching.service;

import com.serveme.service.searching.domain.UserExDTO;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public interface UserService {
    UserExDTO getUser(String accessToken);
}
