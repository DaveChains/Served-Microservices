package com.millennialslab.served.service.dao;

import com.millennialslab.served.service.domain.AccessTokenDomain;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public interface AccesTokenDAO {
    void insert(AccessTokenDomain userToken);

    void delete(long userId);
}
