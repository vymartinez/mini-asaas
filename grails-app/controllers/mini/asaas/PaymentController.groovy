package mini.asaas

import grails.validation.ValidationException

import mini.asaas.BaseController
import mini.asaas.enums.MessageType
import mini.asaas.payment.Payment
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.PaymentService


class PaymentController extends BaseController {

    PaymentService paymentService

    static allowedMethods = [
            save: "POST",
            update: "PUT",
            delete: "DELETE",
            confirmCashPayment: "POST"
    ]

    def index() {
        params.max = getLimitPerPage()
        params.offset = getOffset()

        List<Payment> payments = paymentService.list(params, params.max as Integer, params.offset as Integer)
        Long paymentCount = paymentService.count(params)

        render(view: 'index', model: [payments: payments, paymentCount: paymentCount])
    }

    def show(Long id) {
        try {
            Payment payment = paymentService.getById(id)
            render(view: 'show', model: [payment: payment])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def create() {
        SavePaymentAdapter adapter = new SavePaymentAdapter(params)
        render(view: 'create', model: [adapter: adapter])
    }

    def save(SavePaymentAdapter adapter) {
        try {
            Payment payment = paymentService.create(adapter)
            String flashMsg = "${message(code: "payment.created.success", args: [payment.id])}"
            buildFlashAlert(
                    flashMsg,
                    MessageType.SUCCESS,
                    true
            )
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String errMsg = "${message(code: "payment.created.error")}"
            buildFlashAlert(
                    errMsg,
                    MessageType.ERROR,
                    false
            )
            render(view: "create", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            render(view: "create", model: [adapter: adapter])
        }
    }

    def edit(Long id) {
        try {
            Payment payment = paymentService.getById(id)
            SavePaymentAdapter adapter = new SavePaymentAdapter(payment)
            render(view: 'edit', model: [adapter: adapter])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def update(SavePaymentAdapter adapter) {
        try {
            Payment payment = paymentService.update(adapter)
            String msg = "${message(code: "payment.updated.success", args: [payment.id])}"
            buildFlashAlert(
                    msg,
                    MessageType.SUCCESS,
                    true
            )
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String err = "${message(code: "payment.updated.error")}"
            buildFlashAlert(
                    err,
                    MessageType.ERROR,
                    false
            )
            render(view: "edit", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def delete(Long id) {
        try {
            paymentService.delete(id)
            String msg = "${message(code: "payment.deleted.success")}"
            buildFlashAlert(
                    msg,
                    MessageType.SUCCESS,
                    true
            )
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }
        redirect(action: "index")
    }

    def confirmCashPayment(Long id) {
        try {
            paymentService.confirmCashPayment(id)
            String msg = "${message(code: "payment.confirmed.success")}"
            buildFlashAlert(
                    msg,
                    MessageType.SUCCESS,
                    true
            )
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }
        redirect(action: "show", id: id)
    }
}