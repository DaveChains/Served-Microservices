package com.serveme.service.service;

import com.serveme.service.domain.UserExDTO;

/**
 * Created by DavidChains.
 */
public interface UserService {
    UserExDTO getUser(String accessToken);
}
