package com.millennialslab.served.service.api.dto.output;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CardOutputDto {

    @NotNull
    private String cardToken;

    private String cardType;

    private String cardLastDigits;

    CardOutputDto() {

    }

    public CardOutputDto( String cardToken, String cardType, String cardLastDigits) {
        this.cardToken = cardToken;
        this.cardType = cardType;
        this.cardLastDigits = cardLastDigits;
    }

    public String getCardLastDigits() {
        return cardLastDigits;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardToken() {
        return cardToken;
    }

}
