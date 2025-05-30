package mini.asaas.dtos

import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus

import java.time.LocalDate

class PaymentDTO {
    BillingType billingType
    BigDecimal value
    PaymentStatus status
    LocalDate overdueDate
    Long payerId

    PaymentDTO(Map params) {
        this.billingType = BillingType.convert(params.billingType.toString())
        this.value = BigDecimal.valueOf(Double.parseDouble(params.value.toString()))
        this.status = PaymentStatus.convert(params.status.toString())
        this.overdueDate = LocalDate.now().plusMonths(1)
        this.payerId = Long.parseLong(params.payerId.toString())
    }
}
