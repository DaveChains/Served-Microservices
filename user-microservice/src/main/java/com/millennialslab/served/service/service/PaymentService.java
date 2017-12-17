package com.millennialslab.served.service.service;

import com.millennialslab.served.service.api.dto.input.PaymentInfoDto;

public interface PaymentService {
    boolean addPaymentCard(String accessToken, long userId, String cardToken, String cardType, String cardLastDigits);

    PaymentInfoDto getCurrentCard(String accessToken, long userId);
}
