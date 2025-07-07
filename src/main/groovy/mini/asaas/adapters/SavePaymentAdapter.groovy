package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.payment.Payment
import mini.asaas.utils.BigDecimalUtils

import java.math.RoundingMode
import java.text.SimpleDateFormat

class SavePaymentAdapter {

    Long id

    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    static constraints = {
        id nullable: true
        value min: new BigDecimal("0.01")
    }

    public SavePaymentAdapter(Map params) {
        this.id = params.id?.toString()?.toLong()
        this.payerId = params.payerId?.toString()?.toLong()
        this.billingType = BillingType.valueOf(params.billingType as String)

        if (params.value) {
            this.value = new BigDecimal(params.value.toString()).setScale(2, RoundingMode.HALF_UP)
        } else {
            this.value = null
        }

        if (params.dueDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
            this.dueDate = sdf.parse(params.dueDate as String)
        } else {
            this.dueDate = null
        }
    }
}