package mini.asaas

import grails.validation.ValidationException

import mini.asaas.BaseController
import mini.asaas.enums.MessageType
import mini.asaas.payment.Payment
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.PaymentService


class PaymentController extends BaseController {

    PaymentService paymentService

    def index() {
        try {
            List<Payment> payments = paymentService.list(params, getLimitPerPage(), getOffset())
            return payments
        } catch (Exception exception) {
            String msg = "${message(code: "payment.index.error")}"
            buildFlashAlert(msg, MessageType.ERROR, false)
            redirect(url: '/dashboard')
        }
    }

    def show() {
        try {
            Payment payment = paymentService.getById(params.id as Long, params.payerId as Long)
            render(view: 'show', model: [payment: payment])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def create() {
        try {
            SavePaymentAdapter adapter = new SavePaymentAdapter(params)
            render(view: 'create', model: [adapter: adapter])
        } catch (Exception exception) {
            String msg = "${message(code: "payment.create.error")}"
            buildFlashAlert(msg, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def save() {
        try {
            SavePaymentAdapter adapter = new SavePaymentAdapter(params)
            Payment payment = paymentService.create(adapter)
            String flashMsg = "${message(code: "payment.created.success", args: [payment.id])}"
            buildFlashAlert(flashMsg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String errMsg = "${message(code: "payment.created.error")}"
            buildFlashAlert(errMsg, MessageType.ERROR, false)
            render(view: "create", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            render(view: "create", model: [adapter: adapter])
        }
    }

    def edit() {
        try {
            Payment payment = paymentService.getById(params.id as Long, params.payerId as Long)
            SavePaymentAdapter adapter = new SavePaymentAdapter(payment)
            render(view: 'edit', model: [adapter: adapter])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def update() {
        SavePaymentAdapter adapter = new SavePaymentAdapter(params)
        try {
            Payment payment = paymentService.update(adapter)
            String msg = "${message(code: "payment.updated.success", args: [payment.id])}"
            buildFlashAlert(msg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String err = "${message(code: "payment.updated.error")}"
            buildFlashAlert(err, MessageType.ERROR, false)
            render(view: "edit", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def delete() {
        try {
            paymentService.delete(params.id as Long, params.payerId as Long)
            String msg = "${message(code: "payment.deleted.success")}"
            buildFlashAlert(msg, MessageType.SUCCESS, true)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }

        redirect(action: "index")
    }

    def confirmCashPayment() {
        try {
            paymentService.confirmCashPayment(params.id as Long, params.payerId as Long)
            String msg = "${message(code: "payment.confirmed.success")}"
            buildFlashAlert(msg, MessageType.SUCCESS, true)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }

        redirect(action: "show", id: id)
    }

    def restore() {
        try {
            Payment payment = paymentService.restore(params.id as Long, params.payerId as Long)
            String msg = "${message(code: 'payment.restored.success', args: [payment.id])}"
            buildFlashAlert(msg, MessageType.SUCCESS, true)
        } catch (ValidationException exception) {
            buildFlashAlert("${message(code: 'payment.restored.error')}", MessageType.ERROR, false)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }

        redirect(action: 'index')
    }
}