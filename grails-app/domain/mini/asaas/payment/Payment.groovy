package mini.asaas.payment

import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
import mini.asaas.Payer
import mini.asaas.utils.BaseEntity

class Payment extends BaseEntity {

    Payer payer

    BillingType billingType

    BigDecimal value

    PaymentStatus status

    Date dueDate

    static constraints = {
        value min: BigDecimal("0.01")
    }

}