package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.validation.ValidationException

import mini.asaas.BaseController
import mini.asaas.adapters.UpdatePaymentAdapter
import mini.asaas.enums.MessageType
import mini.asaas.payment.Payment
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.PaymentService
import mini.asaas.utils.user.UserUtils

@GrailsCompileStatic
class PaymentController extends BaseController {

    PaymentService paymentService

    def index() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        try {
            List<Payment> payments = paymentService.list(params, getLimitPerPage(), getOffset(), customer.id)
            return payments
        } catch (Exception exception) {
            String msg = "${message(code: "payment.index.error")}"
            buildFlashAlert(msg, MessageType.ERROR, false)
            redirect(url: '/dashboard')
        }
    }

    def show() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        try {
            Payment payment = paymentService.getById(params.id as Long, params.payerId as Long, customer.id)
            redirect(view: 'show', model: [payment: payment])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def create() {
        return redirect(view: 'create')
    }

    def save() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        SavePaymentAdapter adapter = new SavePaymentAdapter(params)
        try {
            Payment payment = paymentService.create(adapter, customer.id)
            String flashMsg = "${message(code: "payment.created.success", args: [payment.id])}"
            buildFlashAlert(flashMsg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String errMsg = "${message(code: "payment.created.error")}"
            buildFlashAlert(errMsg, MessageType.ERROR, false)
            redirect(view: "create", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(view: "create", model: [adapter: adapter])
        }
    }

    def edit() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        try {
            Payment payment = paymentService.getById(params.id as Long, params.payerId as Long, customer.id)
            redirect(view: 'edit', model: [payment: payment])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def update() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        UpdatePaymentAdapter adapter = new UpdatePaymentAdapter(params)
        try {
            Payment payment = paymentService.update(adapter, customer.id)
            String msg = "${message(code: "payment.updated.success", args: [payment.id])}"
            buildFlashAlert(msg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String err = "${message(code: "payment.updated.error")}"
            buildFlashAlert(err, MessageType.ERROR, false)
            redirect(view: "edit", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def delete() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        try {
            paymentService.delete(params.id as Long, params.payerId as Long, customer.id)
            String msg = "${message(code: "payment.deleted.success")}"
            buildFlashAlert(msg, MessageType.SUCCESS, true)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }

        redirect(action: "index")
    }

    def confirmCashPayment() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        try {
            Payment payment = paymentService.confirmCashPayment(params.id as Long, params.payerId as Long, customer.id)
            String msg = "${message(code: "payment.confirmed.success")}"
            buildFlashAlert(msg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: 'index')
        }
    }

    def restore() {
        Customer customer = UserUtils.getCurrentCustomer(true)
        try {
            Payment payment = paymentService.restore(params.id as Long, params.payerId as Long, customer.id)
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