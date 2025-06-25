package mini.asaas

import grails.validation.ValidationException

import mini.asaas.BaseController
import mini.asaas.enums.MessageType
import mini.asaas.payment.Payment
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.payment.PaymentService


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

        respond payments, model: [paymentCount: paymentCount]
    }

    def show(Long id) {
        try {
            Payment payment = paymentService.getById(id)
            respond payment
        } catch (RuntimeException e) {
            buildFlashAlert(e.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def create() {
        respond new SavePaymentAdapter(params)
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
            SavePaymentAdapter adapter = new SavePaymentAdapter()
            adapter.id = payment.id
            adapter.billingType = payment.billingType
            adapter.value = payment.value
            adapter.dueDate = payment.dueDate
            respond adapter
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
            redirect action: "index"
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