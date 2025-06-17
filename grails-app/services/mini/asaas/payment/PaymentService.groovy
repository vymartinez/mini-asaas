package mini.asaas.payment

import grails.compiler.GrailsCompileStatic
import mini.asaas.Payer
import mini.asaas.adapters.UpdatePaymentAdapter
import mini.asaas.enums.PaymentStatus
import mini.asaas.notification.EmailNotificationService
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.utils.PaginationUtils

import grails.plugin.springsecurity.SpringSecurityService
import grails.gorm.transactions.Transactional
import org.springframework.security.core.userdetails.User


@GrailsCompileStatic
@Transactional
class PaymentService extends PaginationUtils {

    EmailNotificationService emailNotificationService

    SpringSecurityService springSecurityService

    public Payment create(SavePaymentAdapter adapter) {
        User currentUser = springSecurityService.currentUser as User

        Payer payer = Payer.get(adapter.payerId)

        if (!payer) {
            throw new RuntimeException("Payer não encontrado para ID ${adapter.payerId}")
        }

        if (payer.customer?.email != currentUser.username) {
            throw new RuntimeException("Acesso negado: não é o dono do pagador.")
        }

        Payment payment = new Payment(
                payer: payer,
                billingType: adapter.billingType,
                value: adapter.value,
                dueDate: adapter.dueDate,
                status: PaymentStatus.PENDING
        )

        payment.save(failOnError: true)

        emailNotificationService.notifyCreated(payment)

        return  payment
    }

    public Payment update(Long id, UpdatePaymentAdapter adapter) {
        User currentUser = springSecurityService.currentUser as User

        Payment payment = Payment.get(id)

        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID ${id}")
        }

        if (payment.payer.email != currentUser.username) {
            throw new RuntimeException("Acesso negado: não é o dono da cobrança.")
        }

        if (adapter.updatePayer) {
            Payer newPayer = Payer.get(adapter.payerId)

            if (!newPayer) {
                throw new RuntimeException("Payer não encontrado para ID ${adapter.payerId}")
            }

            payment.payer = newPayer
        }

        if (adapter.updateBillingType) {
            payment.billingType = adapter.billingType
        }

        if (adapter.updateValue) {
            payment.value = adapter.value
        }

        if (adapter.updateDueDate) {
            payment.dueDate = adapter.dueDate
        }

        payment.save(failOnError: true)

        return payment
    }

    public List<Payment> listForCurrentUser(Map params) {
        Map paged = normalizePagination(params)
        String email = springSecurityService.authentication?.name
        Payer payer = Payer.findByEmail(email)
        if (!payer) {
            return []
        }
        return Payment.findAllByPayer(payer, paged)
    }

    public Long countForCurrentUser() {
        String email = springSecurityService.authentication?.name
        Payer payer = Payer.findByEmail(email)
        if (!payer) {
            return 0L
        }
        return Payment.countByPayer(payer)
    }

    public void softDelete(Long id) {
        User currentUser = springSecurityService.currentUser as User

        Payment payment = Payment.get(id)

        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID ${id}")
        }

        if (payment.payer.email != currentUser.username) {
            throw new RuntimeException("Acesso negado: não é o dono da cobrança")
        }

        payment.deleted = true
        payment.save(failOnError: true)
        emailNotificationService.notifyDeleted(payment)
    }

    public void restore(Long id) {
        Payment.where { id == id }.updateAll(deleted: false)
    }

    public void confirm(Long id) {
        User currentUser = springSecurityService.currentUser as User

        Payment payment = Payment.get(id)

        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID ${id}")
        }

        if (payment.payer.email != currentUser.username) {
            throw new RuntimeException("Acesso negado: não é o dono da cobrança")
        }

        if (payment.status != PaymentStatus.PENDING) {
            throw new RuntimeException("Só é possível confirmar pagamentos pendentes (PENDING)")
        }

        payment.status = PaymentStatus.RECEIVED
        payment.save(failOnError: true)
        emailNotificationService.notifyPaid(payment)
    }
}
