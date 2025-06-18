package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.utils.DateUtils

import java.text.SimpleDateFormat

import groovy.transform.CompileStatic

@CompileStatic
class UpdatePaymentAdapter {
    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    public UpdatePaymentAdapter(Map params) {
        if (params.containsKey('payerId')) {
            this.payerId = params.payerId as Long
        }

        if (params.containsKey('billingType') && params.billingType) {
            this.billingType = BillingType.valueOf(params.billingType.toString().toUpperCase())
        }

        if (params.containsKey('value') && params.value) {
            this.value = new BigDecimal(params.value.toString())
        }

        if (params.containsKey('dueDate') && params.dueDate) {
            this.dueDate = new SimpleDateFormat(DateUtils.ISO_DATE).parse(params.dueDate.toString())
        }
    }
}
