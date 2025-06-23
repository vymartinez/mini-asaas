package mini.asaas.payment

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.Payer
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.adapters.UpdatePaymentAdapter
import mini.asaas.enums.PaymentStatus

@GrailsCompileStatic
@Transactional
class PaymentService {

    SpringSecurityService springSecurityService

    public List<Payment> listForCurrentUser(Map params) {
        Payer payer = springSecurityService.currentUser as Payer
        Payment.findAllByPayer(payer, params)
    }

    public Long countForCurrentUser(Map params) {
        Payer payer = springSecurityService.currentUser as Payer
        Payment.countByPayer(payer)
    }

    public Payment getOwnedOrFail(Long id) {
        Payment payment = Payment.get(id)
        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID $id")
        }
        if (payment.payer != springSecurityService.currentUser) {
            throw new RuntimeException("Acesso negado: você não é o dono deste pagamento.")
        }
        return payment
    }

    public Payment create(SavePaymentAdapter adapter) {
        Payer payer = springSecurityService.currentUser as Payer

        Payment payment = new Payment()
        payment.payer = payer
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate

        if (!payment.save(flush: true)) {
            payment.errors.allErrors.each { log.error it }
            throw new RuntimeException("Erro ao salvar pagamento.")
        }
        return payment
    }

    public Payment update(Long id, UpdatePaymentAdapter adapter) {
        Payment payment = getOwnedOrFail(id)
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate

        if (!payment.save(flush: true)) {
            payment.errors.allErrors.each { log.error it }
            throw new RuntimeException("Erro ao atualizar pagamento.")
        }
        return payment
    }

    public void delete(Long id) {
        Payment payment = getOwnedOrFail(id)
        payment.delete(flush: true)
    }

    public void processDuePayments() {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        Date today = calendar.time
        Payment.findAllByDueDateLessThanEqualsAndStatus(today, PaymentStatus.PENDING).each { payment ->
            payment.status = PaymentStatus.OVERDUE

            if (!payment.save(flush: true)) {
                log.error("Falha ao marcar pagamento ${payment.id} como vencido: ${payment.errors}")
            }
        }
    }
}