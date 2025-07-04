package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import mini.asaas.BaseController
import mini.asaas.adapters.UpdatePaymentAdapter
import mini.asaas.enums.MessageType
import mini.asaas.payment.Payment
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.PaymentService
import mini.asaas.utils.user.UserUtils
import org.springframework.context.MessageSource


@Secured(['IS_AUTHENTICATED_FULLY'])
@GrailsCompileStatic
class PaymentController extends BaseController {

    PaymentService paymentService

    MessageSource messageSource

    def index() {
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            List<Payment> payments = paymentService.list(params, getLimitPerPage(), getOffset(), customer.id)
            return payments
        } catch (Exception exception) {
            String msg = messageSource.getMessage("payment.index.error", null, request.locale)
            buildFlashAlert(msg, MessageType.ERROR, false)
            redirect(url: '/dashboard')
        }
    }

    def show() {
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            Payment payment = paymentService.getById(params.id as Long, params.payerId as Long, customer.id)
            redirect(view: 'show', model: [payment: payment])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def create() {
        return render(view: 'create')
    }


    def save() {
        SavePaymentAdapter adapter = new SavePaymentAdapter(params)
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            Payment payment = paymentService.create(adapter, customer.id)
            String flashMsg = messageSource.getMessage("payment.created.success", [payment.id] as Object[], request.locale)
            buildFlashAlert(flashMsg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String errMsg = messageSource.getMessage("payment.created.error", null, request.locale)
            buildFlashAlert(errMsg, MessageType.ERROR, false)
            redirect(view: "create", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(view: "create", model: [adapter: adapter])
        }
    }

    def edit() {
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            Payment payment = paymentService.getById(params.id as Long, params.payerId as Long, customer.id)
            redirect(view: 'edit', model: [payment: payment])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def update() {
        UpdatePaymentAdapter adapter = new UpdatePaymentAdapter(params)
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            Payment payment = paymentService.update(adapter, customer.id)
            String msg = messageSource.getMessage("payment.updated.success", [payment.id] as Object[], request.locale)
            buildFlashAlert(msg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (ValidationException exception) {
            String err = messageSource.getMessage("payment.updated.error", null, request.locale)
            buildFlashAlert(err, MessageType.ERROR, false)
            redirect(view: "edit", model: [adapter: adapter, errors: exception.errors])
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: "index")
        }
    }

    def delete() {
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            paymentService.delete(params.id as Long, params.payerId as Long, customer.id)
            String msg = messageSource.getMessage("payment.deleted.success", null, request.locale)
            buildFlashAlert(msg, MessageType.SUCCESS, true)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }

        redirect(action: "index")
    }

    def confirmCashPayment() {
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            Payment payment = paymentService.confirmCashPayment(params.id as Long, params.payerId as Long, customer.id)
            String msg = messageSource.getMessage("payment.confirmed.success", null, request.locale)
            buildFlashAlert(msg, MessageType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(action: 'index')
        }
    }

    def restore() {
        try {
            Customer customer = UserUtils.getCurrentCustomer(true)
            Payment payment = paymentService.restore(params.id as Long, params.payerId as Long, customer.id)
            String msg = messageSource.getMessage('payment.restored.success', [payment.id] as Object[], request.locale)
            buildFlashAlert(msg, MessageType.SUCCESS, true)
        } catch (ValidationException exception) {
            String err = messageSource.getMessage('payment.restored.error', null, request.locale)
            buildFlashAlert(err, MessageType.ERROR, false)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
        }

        redirect(action: 'index')
    }
}
