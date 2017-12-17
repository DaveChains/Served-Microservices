package com.serveme.service.order.domain;

import com.serveme.service.order.enums.PaymentProviderId;

import java.time.Instant;

public class PaymentDetail {

    private long id;

    private Instant createdAt;

    private Instant updatedAt;

    private long userId;

    private String customerToken;

    private PaymentProviderId paymentProvider;

    private String cardType;

    private String cardLastDigits;

    public PaymentDetail() {
    }

    public String getCardLastDigits() {
        return cardLastDigits;
    }

    public void setCardLastDigits(String cardLastDigits) {
        this.cardLastDigits = cardLastDigits;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerToken() {
        return customerToken;
    }

    public void setCustomerToken(String customerToken) {
        this.customerToken = customerToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PaymentProviderId getPaymentProviderId() {
        return paymentProvider;
    }

    public void setPaymentProviderId(PaymentProviderId paymentProvider) {
        this.paymentProvider = paymentProvider;
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

    @Override
    public String toString() {
        return "PaymentDetail{" +
                "cardLastDigits='" + cardLastDigits + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                ", customerToken='" + customerToken + '\'' +
                ", paymentProviderId='" + paymentProvider + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
