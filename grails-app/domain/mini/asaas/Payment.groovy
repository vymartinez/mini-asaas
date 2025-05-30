package mini.asaas

import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
import mini.asaas.utils.BaseEntity
import java.time.LocalDate

class Payment extends BaseEntity {
    BillingType billingType
    BigDecimal value
    PaymentStatus status
    LocalDate overdueDate
    Payer payer
}
