package com.serveme.payment.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcHelper {

    private static final BigDecimal HUNDRED = new BigDecimal(100);


    public static BigDecimal percentage(BigDecimal amount, BigDecimal perc) {
        return amount.multiply(perc).divide(HUNDRED, 3, BigDecimal.ROUND_HALF_UP);
    }

    public static int toCents(BigDecimal amount) {
        return amount
                .setScale(2, RoundingMode.HALF_UP)
                .multiply(HUNDRED)
                .intValue();
    }


}
