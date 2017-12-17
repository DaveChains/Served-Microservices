package com.millennialslab.served.service.api.dto.input;

import java.io.Serializable;

/**
 * Created by Davids-iMac on 15/11/15.
 */
public class CardInputDTO implements Serializable {

    private String cardToken;

    private String cardType;

    private String cardLastDigits;

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
}
