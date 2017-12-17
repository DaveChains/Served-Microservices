package com.serveme.payment.payment;

import com.serveme.payment.domain.CustomerCharge;
import com.serveme.payment.domain.PaymentDetail;
import com.serveme.payment.enums.PaymentProviderId;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Edgar on 4/10/2016.
 */
public interface PaymentProvider {
//todo DOCUMENTATION

    PaymentProviderId getId();


    String createCustomer(
            String cardToken,
            Map<String, Object> metadata
    ) throws Exception;


    void updateCustomer(
            String providerCustomerId,
            String newCardToken
    )throws Exception;

    ChargeResultWrapper charge(
            String customerToken,
            @Valid CustomerCharge c) throws Exception;


}
