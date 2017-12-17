package com.serveme.payment.service;

import java.math.BigDecimal;

public interface FeeService {
    //SERVEME
    BigDecimal getServemeFeeFixAmount();

    BigDecimal getServemeFeePercentage();

//    BigDecimal getServemeTotalFeeApplied(BigDecimal amount);

    //PAYMENT GATEWAY
    BigDecimal getPaymentGatewayFeePercentage();

    BigDecimal getPaymentGatewayFeeFixedAmount();

//    BigDecimal getPaymentGatewayTotalFeeApplied(BigDecimal amount);

    //RESTAURANT
//    BigDecimal getRestaurantNetBenefit(BigDecimal amount);


    default boolean test(){return  true;}
}