package com.serveme.service.order.api.dto.input;

import java.math.BigDecimal;

/**
 * Created by Edgar on 4/15/2016.
 */
public class ChargeInputDTO {
    private BigDecimal amountCharged;
    private String chargeId;

    public BigDecimal getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(BigDecimal amountCharged) {
        this.amountCharged = amountCharged;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }
}
