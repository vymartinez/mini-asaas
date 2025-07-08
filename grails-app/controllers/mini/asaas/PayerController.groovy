package mini.asaas

import grails.gorm.PagedResultList
import mini.asaas.adapters.SavePayerAdapter
import mini.asaas.enums.MessageType

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@GrailsCompileStatic
class PayerController extends BaseController {

    PayerService payerService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def create() {
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long currentCustomerId = getCurrentCustomerId()
            Payer payer = payerService.create(savePayerAdapter, currentCustomerId)

            buildFlashAlert("Pagador criado com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/payer/list', model: [payer: payer])
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(url: '/payer/create', model: [params: params])
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(url: '/payer/create', model: [params: params])
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/payer/create', model: [params: params])
        }
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @Secured(['IS_AUTHENTICATED_FULLY'])
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

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def selectOptions(Long selectedId) {
        Long customerId = getCurrentCustomerId()
        List<Payer> payers = payerService.listAll(customerId)

        render(template: "/payer/atlasOptions", model: [payers: payers, selectedId: selectedId])
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def register() { }
}
