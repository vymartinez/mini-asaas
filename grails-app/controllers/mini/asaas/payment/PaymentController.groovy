package mini.asaas.payment

import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.adapters.UpdatePaymentAdapter

import grails.gorm.transactions.Transactional

@Transactional(readOnly = true)
class PaymentController {

    PaymentService paymentService
    SpringSecurityService springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.offset = params.offset as Integer ?: 0
        Payment payments = paymentService.listForCurrentUser(params)
        Payment paymentCount = paymentService.countForCurrentUser(params)
        render(view: 'index', model: [payments: payments, paymentCount: paymentCount])
    }

    def show(Long id) {
        Payment payment = paymentService.get(id)
        if (!payment) {
            flash.message = "Pagamento não encontrado para ID $id"
            redirect action: 'index'
            return
        }
        render(view: 'show', model: [payment: payment])
    }

    def create() {
        render(view: 'create', model: [adapter: new SavePaymentAdapter()])
    }

    @Transactional
    def save(SavePaymentAdapter adapter) {
        if (adapter.hasErrors()) {
            render(view: 'create', model: [adapter: adapter])
            return
        }

        try {
            Payment payment = paymentService.create(adapter)
            flash.message = "Cobrança criada com sucesso."
            redirect(action: 'show', id: payment.id)
        } catch (RuntimeException exception) {
            flash.message = exception.message
            render(view: 'create', model: [adapter: adapter])
        }
    }

    def edit(Long id) {
        Payment payment = paymentService.get(id)

        if (!payment) {
            flash.message = "Pagamento não encontrado para ID $id"
            redirect(action: 'index')
            return
        }

        render(view: 'edit', model: [adapter: new UpdatePaymentAdapter(payment)])
    }

    @Transactional
    def update(Long id, UpdatePaymentAdapter adapter) {
        if (adapter.hasErrors()) {
            render(view: 'edit', model: [adapter: adapter])
            return
        }

        try {
            Payment payment = paymentService.update(id, adapter)
            flash.message = "Cobrança atualizada com sucesso."
            redirect(action: 'show', id: payment.id)
        } catch (RuntimeException exception) {
            flash.message = exception.message
            render(view: 'edit', model: [adapter: adapter])
        }
    }

    @Transactional
    def delete(Long id) {
        try {
            paymentService.softDelete(id)
            flash.message = "Cobrança removida com sucesso."
            redirect(action: 'index')
        } catch (RuntimeException exception) {
            flash.message = exception.message
            redirect(action: 'show', id: id)
        }
    }
}