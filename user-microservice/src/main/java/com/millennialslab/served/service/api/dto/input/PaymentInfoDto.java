package com.millennialslab.served.service.api.dto.input;

import com.millennialslab.served.service.enums.PaymentProviderId;

import java.time.Instant;

/**
 * Created by Edgar on 7/3/2016.
 */
public class PaymentInfoDto {
    private long id;

    private Instant createdAt;

    private Instant updatedAt;

    private long userId;

    private String customerToken;

    private PaymentProviderId paymentProvider;

    private String cardType;

    private String cardLastDigits;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    public PaymentProviderId getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(PaymentProviderId paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardLastDigits() {
        return cardLastDigits;
    }

    public void setCardLastDigits(String cardLastDigits) {
        this.cardLastDigits = cardLastDigits;
    }
}
