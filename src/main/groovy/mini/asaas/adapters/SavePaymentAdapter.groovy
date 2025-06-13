package mini.asaas.adapters

import mini.asaas.enums.BillingType

import java.text.SimpleDateFormat

import groovy.transform.CompileStatic

@CompileStatic
class SavePaymentAdapter {
    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    private static final String DATE_FORMAT = 'yyyy-MM-dd'

    public SavePaymentAdapter(Map params) {
        this.payerId = params.payerId as Long
        this.billingType = BillingType.valueOf(params.billingType.toString().toUpperCase())
        this.value = new BigDecimal(params.value.toString())
        this.dueDate = new SimpleDateFormat(DATE_FORMAT).parse(params.dueDate.toString())
    }
}
