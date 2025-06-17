package mini.asaas.payment

import grails.compiler.GrailsCompileStatic
import groovy.transform.CompileDynamic
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

    @CompileDynamic
    @Transactional
    public Payment update(Long id, UpdatePaymentAdapter adapter) {
        User currentUser = springSecurityService.currentUser as User

        Payment payment = Payment.getOwnedOrFail(id, currentUser)

        if (adapter.payerId != null) {
            Payer newPayer = Payer.FindByIdAndCustomerEmail(adapter.payerId, currentUser.username)

            if (!newPayer) {
                throw new RuntimeException("Acesso negado: Payer não encontrado ou não pertence ao usuário.")
            }

            payment.payer = newPayer
        }

        if (adapter.billingType != null) {
            payment.billingType = adapter.billingType
        }

        if (adapter.value != null) {
            payment.value = adapter.value
        }

        if (adapter.dueDate != null) {
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

        Payment payment = Payment.getOwnedOrFail(id, currentUser)

        payment.deleted = true
        payment.save(failOnError: true)
        emailNotificationService.notifyDeleted(payment)
    }

    public void restore(Long id) {
        Payment.where { id == id }.updateAll(deleted: false)
    }

    public void confirm(Long id) {
        User currentUser = springSecurityService.currentUser as User

        Payment payment = Payment.getOwnedOrFail(id, currentUser)

        if (payment.status != PaymentStatus.PENDING) {
            throw new RuntimeException("Só é possível confirmar pagamentos pendentes (PENDING)")
        }

        payment.status = PaymentStatus.RECEIVED
        payment.save(failOnError: true)
        emailNotificationService.notifyPaid(payment)
    }
}
