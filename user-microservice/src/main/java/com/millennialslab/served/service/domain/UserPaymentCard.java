package com.millennialslab.served.service.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Davids-iMac on 15/11/15.
 */
@Table(name = "USER_PAYMENT_CARDS")
public class UserPaymentCard implements Serializable{

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "card_token")
    private String cardToken;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "card_last_digits")
    private String cardLastDigits;

    @Column(name = "stripe_id")
    private String stripeId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
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

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }

    @Override
    public String toString() {
        return "UserPaymentCard{" +
                "cardLastDigits='" + cardLastDigits + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardToken='" + cardToken + '\'' +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }
}
