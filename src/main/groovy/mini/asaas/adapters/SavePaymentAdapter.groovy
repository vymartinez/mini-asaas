package mini.asaas.adapters

import mini.asaas.enums.BillingType

class SavePaymentAdapter {

    Long id

    BillingType billingType

    BigDecimal value

    Date dueDate

    static constraints = {
        id nullable: true
        value min: 0.01G
    }
}