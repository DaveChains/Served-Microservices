package com.serveme.service.order.external.service;

import com.serveme.service.order.domain.PaymentDetail;
import com.serveme.service.order.external.dto.output.UserExDTO;

import java.util.List;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public interface UserService {

    UserExDTO getUser(String accessToken);

    UserExDTO getUserFromId(long id);

    List<UserExDTO> getUserListFromPhone(List<String> phones);

    List<UserExDTO> getUserListFromId(List<Long> IDs);

    PaymentDetail getPaymentMethodForUser(String accessToken, long id);
}
