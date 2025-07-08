package mini.asaas

import grails.gorm.PagedResultList
import mini.asaas.adapters.SavePayerAdapter
import mini.asaas.enums.MessageType

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@GrailsCompileStatic
@Secured(['IS_AUTHENTICATED_FULLY'])
class PayerController extends BaseController {

    PayerService payerService

    def create() {
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long currentCustomerId = getCurrentCustomerId()
            Payer payer = payerService.create(savePayerAdapter, currentCustomerId)

            buildFlashAlert("Pagador criado com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/payer/list', model: [payer: payer])
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(url: '/payer/register', model: [params: params])
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(url: '/payer/register', model: [params: params])
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/payer/register', model: [params: params])
        }
    }

    def show(Long id) {
        try {
            Long currentCustomerId = getCurrentCustomerId()
            Payer payer = payerService.findById(id, currentCustomerId)

            return [payer: payer]
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(url: '/payer/list')
        }
    }

    def list() {
        try {
            Long currentCustomerId = getCurrentCustomerId()
            Integer max = getLimitPerPage()
            Integer offset = getOffset()

            PagedResultList<Payer> payers = payerService.list(params, currentCustomerId, max, offset)

           return [payers: payers, totalCount: payers.totalCount, max: max]
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao listar os pagadores. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/dashboard')
        }
    }

    def update() {
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long payerId = params.payerId as Long
            Long currentCustomerId = getCurrentCustomerId()
            Payer payer = payerService.update(savePayerAdapter, payerId, currentCustomerId)

            buildFlashAlert("Pagador atualizado com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/payer/list', model: [payer: payer])
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(url: '/payer/list', model: [params: params])
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(url: '/payer/list', model: [params: params])
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/payer/list', model: [params: params])
        }
    }

    def disable() {
        try {
            Long payerId = params.payerId as Long
            Long currentCustomerId = getCurrentCustomerId()
            payerService.disable(payerId, currentCustomerId)

            buildFlashAlert("Pagador desativado com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/payer/list')
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao desativar o pagador. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/payer/list')
        }
    }

    def restore() {
        try {
            Long payerId = params.payerId as Long
            Long currentCustomerId = getCurrentCustomerId()
            payerService.restore(payerId, currentCustomerId)

            buildFlashAlert("Pagador reativado com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/payer/list')
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao reativar o pagador. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/payer/list')
        }
    }

    def register() { }
}
