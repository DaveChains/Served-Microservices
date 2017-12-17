package com.serveme.payment.api.dto.payment;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class CardInputDto {


    @NotNull
    private String cardToken;

    private String cardType;

    private String cardLastDigits;


    public String getCardLastDigits() {
        return cardLastDigits;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardToken() {
        return cardToken;
    }


    @Override
    public String toString() {
        return "CardInputDto{" +
                "cardLastDigits='" + cardLastDigits + '\'' +
                ", cardToken='" + cardToken + '\'' +
                ", cardType='" + cardType + '\'' +
                '}';
    }
}
