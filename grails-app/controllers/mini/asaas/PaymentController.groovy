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

@Secured(['IS_AUTHENTICATED_FULLY'])
@GrailsCompileStatic
class PaymentController extends BaseController {

    PayerService payerService
    MessageSource messageSource
    PaymentService paymentService

    def register() {
        return [:]
    }

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
            render(
                    view: 'register',
                    model: [params: params, errors: validationException.errors]
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

    def show(Long id) {
        try {
            Long currentCustomerId = getCurrentCustomerId()
            Payment payment = paymentService.findById(id, currentCustomerId)



            return [payment: payment, customerId: currentCustomerId, billingTypes: BillingType.values(), status: PaymentStatus.values()]
        } catch (RuntimeException re) {
            buildFlashAlert(re.message, MessageType.ERROR, false)
            redirect(url: '/payment/list')
        }
    }

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

    def update() {
        Long customerId = getCurrentCustomerId()
        try {
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

            render(
                    view: 'show',
                    model: [
                            params : params,
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

    def confirmCash(Long paymentId) {
        try {
            Long customerId = getCurrentCustomerId()
            paymentService.confirmCash(paymentId, customerId)
            buildFlashAlert("Cobrança confirmada como recebida em dinheiro!", MessageType.SUCCESS, true)
        } catch (IllegalStateException ise) {
            buildFlashAlert(ise.message, MessageType.INFO, true)
        } catch (Exception e) {
            log.error("Erro ao confirmar recebimento em dinheiro", e)
            buildFlashAlert("Erro ao confirmar a cobrança.", MessageType.ERROR, true)
        }
        redirect(url: "/payment/list")
    }
}