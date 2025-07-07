package mini.asaas

import grails.gorm.PagedResultList
import grails.validation.ValidationException
import grails.plugin.springsecurity.annotation.Secured
import grails.compiler.GrailsCompileStatic
import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
import mini.asaas.payment.Payment
import org.springframework.context.MessageSource

import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.adapters.UpdatePaymentAdapter
import mini.asaas.enums.MessageType

@GrailsCompileStatic
class PaymentController extends BaseController {

    PaymentService paymentService
    PayerService payerService
    MessageSource messageSource

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def register() {
        Long customerId = getCurrentCustomerId()
        Integer max = getLimitPerPage()
        Integer offset = getOffset()
        PagedResultList<Payer> payers =
                payerService.list(params, customerId, max, offset)
        [payers: payers]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create() {
        try {
            SavePaymentAdapter adapter = new SavePaymentAdapter(params)
            Long currentCustomerId = getCurrentCustomerId()
            Payment payment = paymentService.create(adapter, currentCustomerId)

            buildFlashAlert(
                    messageSource.getMessage(
                            'payment.created.success',
                            [payment.id] as Object[],
                            request.locale
                    ),
                    MessageType.SUCCESS,
                    true
            )
            redirect(url: '/payment/list', model: [payment: payment])
        }
        catch (ValidationException validationException) {
            buildFlashAlert(
                    'Atenção: ' + validationException.errors.allErrors*.defaultMessage.join(', '),
                    MessageType.ERROR,
                    false
            )
            Long customerId = getCurrentCustomerId()
            Integer max = getLimitPerPage()
            Integer offset = getOffset()
            PagedResultList<Payer> payers =
                    payerService.list(params, customerId, max, offset)
            render(
                    view: 'register',
                    model: [params: params, payers: payers, errors: validationException.errors]
            )
        }
        catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.message, MessageType.ERROR, false)
            redirect(url: '/payment/register', model: [params: params])
        }
        catch (Exception exception) {
            buildFlashAlert(
                    'Ocorreu um erro interno. Por favor, tente novamente mais tarde.',
                    MessageType.ERROR,
                    false
            )
            redirect(url: '/payment/register', model: [params: params])
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def show(Long id) {
        try {
            Long currentCustomerId = getCurrentCustomerId()
            Payment payment = paymentService.findById(id, currentCustomerId)

            Integer max = getLimitPerPage()
            Integer offset = getOffset()
            PagedResultList<Payer> payers = payerService.list(params, currentCustomerId, max, offset)

            return [payment: payment, payers: payers, billingTypes: BillingType.values(), status: PaymentStatus.values()]
        } catch (RuntimeException re) {
            buildFlashAlert(re.message, MessageType.ERROR, false)
            redirect(url: '/payment/list')
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list() {
        try {
            Long customerId = getCurrentCustomerId()
            Integer max = getLimitPerPage()
            Integer offset = getOffset()
            PagedResultList<Payment> payments =
                    paymentService.list(params, customerId, max, offset)
            [ payments: payments,
              totalCount: payments.totalCount,
              max: max ]
        } catch (Exception exception) {
            buildFlashAlert(
                    'Ocorreu um erro ao listar as cobranças. Por favor, tente novamente mais tarde.',
                    MessageType.ERROR,
                    false
            )
            redirect(url: '/dashboard')
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def update() {
        try {
            Long customerId = getCurrentCustomerId()
            UpdatePaymentAdapter adapter = new UpdatePaymentAdapter(params)

            Payment payment = paymentService.update(adapter, customerId)

            buildFlashAlert(
                    messageSource.getMessage(
                            'payment.updated.success',
                            [payment.id] as Object[],
                            request.locale
                    ),
                    MessageType.SUCCESS,
                    true
            )
            redirect(url: '/payment/list', model: [payment: payment])
        }
        catch (ValidationException validationException) {
            buildFlashAlert(
                    'Atenção: ' + validationException.errors.allErrors*.defaultMessage.join(', '),
                    MessageType.ERROR,
                    false
            )
            Long customerId = getCurrentCustomerId()
            Integer max = getLimitPerPage()
            Integer offset = getOffset()
            PagedResultList<Payer> payers =
                    payerService.list(params, customerId, max, offset)
            render(
                    view: 'show',
                    model: [
                            params : params,
                            payers : payers,
                            payment: paymentService.findById(params.long('id'), customerId),
                            errors : validationException.errors
                    ]
            )
        }
        catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.message, MessageType.ERROR, false)
            redirect(url: '/payment/list')
        }
        catch (Exception exception) {
            log.error("Erro ao atualizar cobrança", exception)
            buildFlashAlert(
                    messageSource.getMessage(
                            'payment.updated.error',
                            null,
                            request.locale
                    ),
                    MessageType.ERROR,
                    false
            )
            redirect(url: '/payment/list')
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def disable() {
        try {
            Long paymentId = params.paymentId as Long
            Long currentCustomerId = getCurrentCustomerId()
            paymentService.disable(paymentId, currentCustomerId)

            buildFlashAlert("Cobrança desativada com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/payment/list')
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao desativar a cobrança. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/payment/list')
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def restore() {
        try {
            Long paymentId = params.paymentId as Long
            Long currentCustomerId = getCurrentCustomerId()
            paymentService.restore(paymentId, currentCustomerId)

            buildFlashAlert("Cobrança reativada com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/payment/list')
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao reativar a cobrança. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/payment/list')
        }
    }
}