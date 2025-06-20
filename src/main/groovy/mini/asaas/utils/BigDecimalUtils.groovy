package mini.asaas.utils

import java.math.RoundingMode

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
abstract class BigDecimalUtils {

    public static BigDecimal round(BigDecimal value, int scale, RoundingMode roundingMode) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(scale, roundingMode)
        }

        return value.setScale(scale, roundingMode)
    }
}
