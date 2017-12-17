package com.serveme.payment.service.impl;

import com.serveme.payment.service.FeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;

@Service
public class FeeServiceImpl implements FeeService {

    private final BigDecimal servemeFeePercentage;

    private final BigDecimal servemeFeeFix;

    private final BigDecimal paymentGatewayFeePercentage;

    private final BigDecimal paymentGatewayFeeFix;

    @Inject
    public FeeServiceImpl(
            @Value("${finance.serveme.fee.percentage}") String servemeFeePercentage,
            @Value("${finance.serveme.fee.fix:0}") String servemeFeeFix,
            @Value("${paymentGateway.fee.percentage}") String paymentGatewayFeePercentage,
            @Value("${paymentGateway.fee.fix}") String paymentGatewayFeeFix) {
        this.servemeFeeFix = new BigDecimal(servemeFeeFix);
        this.servemeFeePercentage = new BigDecimal(servemeFeePercentage);
        this.paymentGatewayFeeFix = new BigDecimal(paymentGatewayFeeFix);
        this.paymentGatewayFeePercentage = new BigDecimal(paymentGatewayFeePercentage);
    }

    public BigDecimal getServemeFeeFixAmount() {
        return servemeFeeFix;
    }

    //Todo in future build infrastructure to allow different fees for restaurant
    public BigDecimal getServemeFeePercentage() {
        return servemeFeePercentage;
    }


    @Override
    public BigDecimal getPaymentGatewayFeePercentage() {
        return paymentGatewayFeePercentage;
    }

    @Override
    public BigDecimal getPaymentGatewayFeeFixedAmount() {
        return paymentGatewayFeeFix;
    }

}
