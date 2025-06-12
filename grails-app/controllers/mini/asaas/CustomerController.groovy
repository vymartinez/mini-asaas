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
            flash.message = message(success: false, error: "Atenção: " + e.errors.allErrors.defaultMessage.join(", "))
            flash.type = MessageType.ERROR
            redirect(url: '/onboarding/createCustomer', model: [params: params])
        } catch (Exception e) {
            flash.message = message(success: false, error: "Ocorreu um erro interno. Por favor, tente novamente mais tarde.")
            flash.type = MessageType.ERROR
            redirect(url: '/onboarding/createCustomer', model: [params: params])
        }
    }
}
