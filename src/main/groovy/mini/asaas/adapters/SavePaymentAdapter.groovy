package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.payment.Payment
import mini.asaas.utils.BigDecimalUtils

import java.math.RoundingMode
import java.text.SimpleDateFormat

class SavePaymentAdapter {

    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    public SavePaymentAdapter(Map params) {
        this.payerId = params.payerId?.toString()?.toLong()
        this.billingType = BillingType.valueOf(params.billingType as String)
        this.value = BigDecimalUtils.roundDefault(params.value as BigDecimal)

        if (params.dueDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
            this.dueDate = sdf.parse(params.dueDate as String)
        } else {
            this.dueDate = null
        }
    }
}