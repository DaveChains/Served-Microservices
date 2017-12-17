package com.serveme.payment.service;

import com.serveme.payment.domain.PaymentDetail;

import java.util.List;

public interface PaymentDetailService {

    PaymentDetail getPaymentDetailByUser(long userId);


    List<PaymentDetail> getPaymentDetailByUserList(List<Long> userId);


    PaymentDetail setCustomerPaymentDetail(
            long userId,
            String providerCustomerToken,
            String cardType,
            String cardLastDigits);


}
