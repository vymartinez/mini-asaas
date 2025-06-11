package mini.asaas.notification

import java.math.RoundingMode

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
@Transactional
class CurrencyFormatterService {
    String format(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP).toString()
    }
}
