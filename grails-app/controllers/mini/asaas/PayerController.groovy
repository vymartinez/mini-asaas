package mini.asaas

import mini.asaas.adapters.SavePayerAdapter
import mini.asaas.enums.MessageType

import grails.compiler.GrailsCompileStatic
import grails.validation.ValidationException

@GrailsCompileStatic
class PayerController extends BaseController {

    PayerService payerService

    def create() {
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long currentCustomerId = 1
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

    def list() {
        try {
            Long currentCustomerId = 1
            List<Payer> payers = payerService.list(params, currentCustomerId, getLimitPerPage(), getOffset())

            return payers
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao listar os pagadores. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/dashboard')
        }
    }

    def update() {
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long payerId = params.payerId as Long
            Long currentCustomerId = 1
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
}
