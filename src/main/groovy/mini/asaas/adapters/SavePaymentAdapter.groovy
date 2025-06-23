package mini.asaas.adapters

import mini.asaas.enums.BillingType
import mini.asaas.utils.DateUtils
import mini.asaas.utils.StringUtils

import java.text.SimpleDateFormat

import groovy.transform.CompileStatic

@CompileStatic
class SavePaymentAdapter {
    Long payerId

    BillingType billingType

    BigDecimal value

    Date dueDate

    public SavePaymentAdapter(Map params) {
        if (params.payerId) {
            this.payerId = params.payerId as Long
        }

        if (params.billingType) {
            String rawBt = params.billingType.toString().trim()

            if (rawBt) {
                this.billingType = BillingType.valueOf(rawBt.toUpperCase())
            }
        }

        if (params.value) {
            String rawVal = params.value.toString().trim()

            if (rawVal) {
                this.value = new BigDecimal(rawVal)
            }
        }

        if (params.dueDate) {
            String rawDate = params.dueDate.toString().trim()

            if (rawDate) {
                this.dueDate = new SimpleDateFormat(DateUtils.ISO_DATE).parse(rawDate)
            }
        }
    }
}
