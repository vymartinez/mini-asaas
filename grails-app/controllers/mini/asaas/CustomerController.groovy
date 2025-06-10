package mini.asaas

import mini.asaas.adapters.SaveCustomerAdapter
import mini.asaas.enums.MessageType

import grails.validation.ValidationException

class CustomerController {

    CustomerService customerService

    def create() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Customer customer = customerService.create(saveCustomerAdapter)

            redirect(view: 'dashboard', model: [customer: customer])
        } catch (ValidationException e) {
            flash.message = message(success: false, error: "Atenção: " + e.errors.allErrors.defaultMessage.join(", "))
            flash.type = MessageType.ERROR
            redirect(url: '/onboarding/createAccount', model: [params: params])
        }
    }

    def update() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Customer customer = customerService.update(saveCustomerAdapter)

            flash.message = message(success: true, message: "Dados alterados com sucesso!")
            flash.type = MessageType.SUCCESS
            redirect(url: '/dashboard', model: [customer: customer])
        } catch (ValidationException e) {
            flash.message = message(success: false, error: "Atenção: " + e.errors.allErrors.defaultMessage.join(", "))
            flash.type = MessageType.ERROR
            redirect(url: '/dashboard/profile', model: [params: params])
        } catch (RuntimeException e) {
            flash.message = message(success: false, error: e.getMessage())
            flash.type = MessageType.ERROR
            redirect(url: '/dashboard')
        }
    }
}
