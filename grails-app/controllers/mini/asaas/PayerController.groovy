package mini.asaas

import mini.asaas.adapters.SavePayerAdapter
import mini.asaas.enums.MessageType

import grails.compiler.GrailsCompileStatic
import grails.gorm.PagedResultList
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
            payerService.create(savePayerAdapter, currentCustomerId)

            buildFlashAlert("Pagador criado com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'payer', action: 'list')
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'register', params: params)
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'register', params: params)
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'register', params: params)
        }
    }

    def show(Long id) {
        try {
            Long currentCustomerId = getCurrentCustomerId()
            Payer payer = payerService.findById(id, currentCustomerId)

            return [payer: payer]
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'list')
        }
    }

    def findAll() {
        try {
            Long currentCustomerId = getCurrentCustomerId()
            Integer max = getLimitPerPage()
            Integer offset = getOffset()

            PagedResultList<Payer> payers = payerService.list(params, currentCustomerId, max, offset)

           render(template: '/payer/templates/search', model: [payers: payers, totalCount: payers.totalCount, max: max], params: params)
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao listar os pagadores. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(controller: 'dashboard', action: 'index')
        }
    }

    def update() {
        Long payerId = params.payerId as Long
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long currentCustomerId = getCurrentCustomerId()
            payerService.update(savePayerAdapter, payerId, currentCustomerId)

            buildFlashAlert("Pagador atualizado com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'payer', action: 'list')
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'show', id: payerId, params: params)
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'show', id: payerId, params: params)
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'show', id: payerId, params: params)
        }
    }

    def disable() {
        try {
            Long payerId = params.payerId as Long
            Long currentCustomerId = getCurrentCustomerId()
            payerService.disable(payerId, currentCustomerId)

            buildFlashAlert("Pagador desativado com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'payer', action: 'list')
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao desativar o pagador. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'list')
        }
    }

    def restore() {
        try {
            Long payerId = params.payerId as Long
            Long currentCustomerId = getCurrentCustomerId()
            payerService.restore(payerId, currentCustomerId)

            buildFlashAlert("Pagador reativado com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'payer', action: 'list')
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao reativar o pagador. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(controller: 'payer', action: 'list')
        }
    }

    def register() { }

    def list() { }
}
