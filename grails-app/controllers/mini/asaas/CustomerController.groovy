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
            redirect(url: '/', model: [params: params])
        }
    }
}
