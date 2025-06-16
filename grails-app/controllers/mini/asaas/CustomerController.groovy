package mini.asaas

import mini.asaas.adapters.SaveCustomerAdapter
import mini.asaas.enums.MessageType

import grails.compiler.GrailsCompileStatic
import grails.validation.ValidationException

@GrailsCompileStatic
class CustomerController {

    CustomerService customerService

    def create() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Customer customer = customerService.create(saveCustomerAdapter)

            redirect(view: 'dashboard', model: [customer: customer])
        } catch (ValidationException e) {
            flash.message = "Atenção: " + e.errors.allErrors.defaultMessage.join(", ")
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/onboarding/createCustomer', model: [params: params])
        } catch (Exception e) {
            flash.message = "Ocorreu um erro interno. Por favor, tente novamente mais tarde."
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/onboarding/createCustomer', model: [params: params])
        }
    }

    def update() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Customer customer = customerService.update(saveCustomerAdapter)

            flash.message = "Dados alterados com sucesso!"
            flash.success = true
            flash.type = MessageType.SUCCESS
            redirect(url: '/dashboard', model: [customer: customer])
        } catch (ValidationException e) {
            flash.message = "Atenção: " + e.errors.allErrors.defaultMessage.join(", ")
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/dashboard/profile', model: [params: params])
        } catch (Exception e) {
            flash.message = "Ocorreu um erro interno. Por favor, tente novamente mais tarde."
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(url: '/dashboard')
        }
    }
}
