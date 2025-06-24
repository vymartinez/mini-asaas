package mini.asaas

import mini.asaas.adapters.SaveCustomerAdapter
import mini.asaas.enums.MessageType

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@GrailsCompileStatic
class CustomerController {

    CustomerService customerService

    @Secured("permitAll")
    def create() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Customer customer = customerService.create(saveCustomerAdapter)

            redirect(url: '/dashboard', model: [customer: customer])
        } catch (ValidationException e) {
            flash.message = "Atenção: " + e.errors.allErrors.defaultMessage.join(", ")
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(controller: 'onboarding', action: 'createCustomer', params: params)
        } catch (Exception e) {
            flash.message = "Ocorreu um erro interno. Por favor, tente novamente mais tarde."
            flash.success = false
            flash.type = MessageType.ERROR
            redirect(controller: 'onboarding', action: 'createCustomer', params: params)
        }
    }
}
