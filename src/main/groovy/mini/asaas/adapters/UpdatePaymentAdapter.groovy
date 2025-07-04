package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.payment.Payment
import mini.asaas.utils.BigDecimalUtils

import java.math.RoundingMode
import java.text.SimpleDateFormat


class UpdatePaymentAdapter {

    Long id

    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    static contraints = {
        value min: 0.01
    }

    public UpdatePaymentAdapter(Map params) {
        this.id = params.id?.toString()?.toLong()
        this.payerId = params.payerId?.toString()?.toLong()
        this.billingType = params.billingType as BillingType
        this.value = BigDecimalUtils.round(params.value as BigDecimal, 2, RoundingMode.HALF_UP)
        this.dueDate = payment.dueDate

        if (params.dueDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
            this.dueDate = sdf.parse(params.dueDate)
        } else {
            this.dueDate = null
        }
    }
}
