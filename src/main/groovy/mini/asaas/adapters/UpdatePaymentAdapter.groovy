package mini.asaas.adapters

import mini.asaas.enums.BillingType
import java.text.SimpleDateFormat

import groovy.transform.CompileStatic

@CompileStatic
class UpdatePaymentAdapter {
    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    boolean updatePayer = false

    boolean updateBillingType = false

    boolean updateValue = false

    boolean updateDueDate = false

    private static final String DATE_FORMAT = 'yyyy-MM-dd'

    public UpdatePaymentAdapter(Map params) {
        if (params.containsKey('payerId')) {
            this.payerId = params.payerId as Long
            this.updatePayer = true
        }

        if (params.containsKey('billingType') && params.billingType) {
            this.billingType = BillingType.valueOf(params.billingType.toString().toUpperCase())
            this.updateBillingType = true
        }

        if (params.containsKey('value') && params.value) {
            this.value = new BigDecimal(params.value.toString())
            this.updateValue = true
        }

        if (params.containsKey('dueDate') && params.dueDate) {
            this.dueDate = new SimpleDateFormat(DATE_FORMAT).parse(params.dueDate.toString())
            this.updateDueDate = true
        }
    }
}
