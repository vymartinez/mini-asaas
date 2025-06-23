package mini.asaas.payment

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.Payer
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.adapters.UpdatePaymentAdapter

@GrailsCompileStatic
@Transactional
class PaymentService {

    SpringSecurityService springSecurityService

    List<Payment> listForCurrentUser(Map params) {
        Payer payer = springSecurityService.currentUser as Payer
        Payment.findAllByPayer(payer, params)
    }

    Long countForCurrentUser(Map params) {
        Payer payer = springSecurityService.currentUser as Payer
        Payment.countByPayer(payer)
    }

    Payment getOwnedOrFail(Long id) {
        Payment payment = Payment.get(id)
        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID $id")
        }
        if (payment.payer != springSecurityService.currentUser) {
            throw new RuntimeException("Acesso negado: você não é o dono deste pagamento.")
        }
        return payment
    }

    Payment create(SavePaymentAdapter adapter) {
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

    Payment update(Long id, UpdatePaymentAdapter adapter) {
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

    void delete(Long id) {
        Payment payment = getOwnedOrFail(id)
        payment.delete(flush: true)
    }
}