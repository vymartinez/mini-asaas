package mini.asaas.peyment

import mini.asaas.Payer
import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
import mini.asaas.utils.BaseEntity
import java.time.LocalDate

class Payment extends BaseEntity {

    Payer payer

    BillingType billingType

    BigDecimal value

    PaymentStatus status

    LocalDate dueDate

    static constraints = {
        value min: BigDecimal("0.01")
    }

}