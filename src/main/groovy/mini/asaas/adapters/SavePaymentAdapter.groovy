package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.payment.Payment

class SavePaymentAdapter {

    Long id

    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    static constraints = {
        id nullable: true
        value min: 0.01G
    }

    public SavePaymentAdapter(Payment payment) {
        this.id = payment.id
        this.payerId = payment.payer?.id
        this.billingType = payment.billingType
        this.value = payment.value
        this.dueDate = payment.dueDate
    }
}