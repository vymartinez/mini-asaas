package mini.asaas.utils

import java.math.RoundingMode

class BigDecimalUtils {

    private BigDecimalUtils() {}

    public static String formatTwoDecimals(BigDecimal value) {
        if (value == null) {
            return "0.00"
        }

        return value.setScale(2, RoundingMode.HALF_UP).toString()
    }
}
