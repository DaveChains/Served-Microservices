package com.serveme.payment.domain;


import com.serveme.payment.enums.PaymentProviderId;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "PAYMENT_DETAILS")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"})},
        indexes = {@Index(name = "index_user", columnList = "user_id")})
public class PaymentDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "customer_token", nullable = false)
    private String customerToken;

    @Column(name = "payment_provider", nullable = false)
    private PaymentProviderId paymentProvider;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_last_digits")
    private String cardLastDigits;


    public PaymentDetail(){}

    public PaymentDetail(PaymentProviderId paymentProvider, long userId, String customerToken){
        this.createdAt          = Instant.now();
        this.updatedAt          = Instant.now();
        this.userId             = userId;
        this.customerToken = customerToken;
        this.paymentProvider = paymentProvider;
    }

    public PaymentDetail(PaymentProviderId paymentProvider, long userId, String customerToken, String cardType, String cardLastDigits){
        this.createdAt          = Instant.now();
        this.updatedAt          = Instant.now();
        this.userId             = userId;
        this.customerToken = customerToken;
        this.cardType           = cardType;
        this.cardLastDigits     = cardLastDigits;
        this.paymentProvider = paymentProvider;
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
