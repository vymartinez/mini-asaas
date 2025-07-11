package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
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

    PaymentStatus status

    public UpdatePaymentAdapter(Map params) {
        this.id = params.id?.toString()?.toLong()
        this.payerId = params.payerId?.toString()?.toLong()
        this.billingType = BillingType.valueOf(params.billingType as String)
        this.value = BigDecimalUtils.roundDefault(params.value as BigDecimal)

        if (params.dueDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd")
            this.dueDate = sdf.parse(params.dueDate as String)
        } else {
            this.dueDate = null
        }

        if (params.status) {
            this.status = PaymentStatus.valueOf(params.status as String)
        }
    }
}