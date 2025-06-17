package mini.asaas.payment

import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
import mini.asaas.Payer
import mini.asaas.utils.BaseEntity
import org.springframework.security.core.userdetails.User

class Payment extends BaseEntity {

    Payer payer

    BillingType billingType

    BigDecimal value

    PaymentStatus status

    Date dueDate

    static constraints = {
        value min: BigDecimal("0.01")
    }

    public boolean isOwnedBy(User user) {
        return payer?.customer?.email == user.username
    }

    public void assertOwnedBy(User user) {
        if (!isOwnedBy(user)) {
            throw new RuntimeException("Acesso negado: você não é o dono deste pagamento.")
        }
    }

    public static Payment getOwnedOrFail(Long id, User user) {
        Payment payment = Payment.get(id)
        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID ${id}.")
        }

        payment.assertOwnedBy(user)
        return payment
    }

}