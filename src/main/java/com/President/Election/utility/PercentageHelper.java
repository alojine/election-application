package com.President.Election.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PercentageHelper {

    public static BigDecimal getPercentage(int unit, int divisor) {
        if(divisor == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal percentage = BigDecimal.valueOf((double) unit / divisor * 100);
        return percentage.setScale(2, RoundingMode.HALF_UP);
    }
}
