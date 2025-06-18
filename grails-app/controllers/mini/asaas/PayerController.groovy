package mini.asaas

import mini.asaas.adapters.SavePayerAdapter
import mini.asaas.enums.MessageType

import grails.compiler.GrailsCompileStatic
import grails.validation.ValidationException

@GrailsCompileStatic
class PayerController {

    PayerService payerService

    def create() {
        try {
            SavePayerAdapter savePayerAdapter = new SavePayerAdapter(params)

            Long currentCustomerId = 1
            Payer payer = payerService.create(savePayerAdapter, currentCustomerId)

            redirect(url: '/payer/list', model: [payer: payer])
        } catch (ValidationException e) {
            flash.message = "Atenção: " + e.errors.allErrors.defaultMessage.join(", ")
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/payer/create', model: [params: params])
        } catch (RuntimeException e) {
            flash.message = e.getMessage()
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/payer/create', model: [params: params])
        } catch (Exception e) {
            flash.message = "Ocorreu um erro interno. Por favor, tente novamente mais tarde."
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/payer/create', model: [params: params])
        }
    }

    def list() {
        try {
            Long currentCustomerId = 1
            List<Payer> payers = payerService.list(params, currentCustomerId)

            return payers
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro ao listar os pagadores. Por favor, tente novamente mais tarde."
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/dashboard')
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
