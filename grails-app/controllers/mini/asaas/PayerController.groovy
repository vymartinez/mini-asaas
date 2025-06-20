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

    def update() {
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long payerId = params.payerId as Long
            Long currentCustomerId = 1
            Payer payer = payerService.update(savePayerAdapter, payerId, currentCustomerId)

            redirect(url: '/payer/list', model: [payer: payer])
        } catch (ValidationException e) {
            flash.message = "Atenção: " + e.errors.allErrors.defaultMessage.join(", ")
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/payer/list', model: [params: params])
        } catch (RuntimeException e) {
            flash.message = e.getMessage()
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/payer/list', model: [params: params])
        } catch (Exception e) {
            flash.message = "Ocorreu um erro interno. Por favor, tente novamente mais tarde."
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/payer/list', model: [params: params])
        }
    }
}
