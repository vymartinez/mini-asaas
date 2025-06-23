package mini.asaas.payment

import grails.plugin.springsecurity.annotation.Secured
import mini.asaas.BaseController
import mini.asaas.enums.MessageType
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.adapters.UpdatePaymentAdapter

@Secured(['permitAll'])
class PaymentController extends BaseController {

    PaymentService paymentService

    def index() {
        params.max = limitPerPage
        params.offset = offset

        List<Payment> payments = paymentService.listForCurrentUser(params)
        Long paymentCount = paymentService.countForCurrentUser(params)
        return [payments: payments, paymentCount: paymentCount]
    }

    def show() {
        Long id
        Payment payment
        try {
            id = params.long('id')
            payment = paymentService.getOwnedOrFail(id)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: 'index')
            return
        }
        return [payment: payment]
    }

    def create() {
        return [adapter: new SavePaymentAdapter(params)]
    }

    def save() {
        SavePaymentAdapter adapter = new SavePaymentAdapter(params)
        try {
            Payment payment = paymentService.create(adapter)
            buildFlashAlert("Pagamento criado com sucesso.", MessageType.SUCCESS, true)
            redirect(action: 'show', id: payment.id)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            render(view: 'create',
                    model: [adapter: adapter])
        }
    }

    def edit() {
        Long id
        Payment payment
        try {
            id = params.long('id')
            payment = paymentService.getOwnedOrFail(id)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: 'index')
            return
        }

        UpdatePaymentAdapter adapter = new UpdatePaymentAdapter(params)
        adapter.payerId = payment.payer.id
        adapter.billingType = payment.billingType
        adapter.value = payment.value
        adapter.dueDate = payment.dueDate

        return [id: id, adapter: adapter]
    }

    def update() {
        Long id = params.long('id')
        UpdatePaymentAdapter adapter = new UpdatePaymentAdapter(params)
        try {
            Payment payment = paymentService.update(id, adapter)
            buildFlashAlert("Pagamento atualizado com sucesso.", MessageType.SUCCESS, true)
            redirect(action: 'show', id: payment.id)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            render(view: 'edit',
                    model: [adapter: adapter])
        }
    }

    def delete() {
        Long id = params.long('id')
        try {
            paymentService.delete(id)
            buildFlashAlert("Pagamento excluído com sucesso.", MessageType.SUCCESS, true)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }
        redirect(action: 'index')
    }
}