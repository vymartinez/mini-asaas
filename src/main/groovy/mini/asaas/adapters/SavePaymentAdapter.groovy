package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.utils.DateUtils

import java.text.SimpleDateFormat

import groovy.transform.CompileStatic

@CompileStatic
class SavePaymentAdapter {
    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    public SavePaymentAdapter(Map params) {
        this.payerId = params.payerId as Long
        this.billingType = BillingType.valueOf(params.billingType.toString().toUpperCase())
        this.value = new BigDecimal(params.value.toString())
        this.dueDate = new SimpleDateFormat(DateUtils.ISO_DATE).parse(params.dueDate.toString())
    }
}
