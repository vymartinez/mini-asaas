package mini.asaas

import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
import mini.asaas.utils.BaseEntity
import java.time.LocalDate

class Payment extends BaseEntity {

    Payer payer

    BillingType billingType

    BigDecimal value

    PaymentStatus status

    LocalDate dueDate


    static constraints = {
        payer nullable: false
        billingType nullable: false
        value nullable: false, min: 0.01G
        status nullable: false
        dueDate nullable: false
    }

    static mapping = {
        version false
    }

    @Override
    String toString() {
        return "Pagamento de ${value} reais para ${payer?.name} - ${status}"
    }

}