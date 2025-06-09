package mini.asaas

import grails.validation.ValidationException
import mini.asaas.adapters.CustomerAdapter
import mini.asaas.enums.MessageType

class CustomerController {

    def customerService

    def create() {
        try {
            CustomerAdapter customerAdapter = new CustomerAdapter(params)

            Customer customer = customerService.create(customerAdapter)

            redirect(view: 'dashboard', model: [customer: customer])
        } catch (ValidationException e) {
            flash.message = message(success: false, error: "Atenção: " + e.errors.allErrors.defaultMessage.join(", "))
            flash.type = MessageType.ERROR
            redirect(url: '/', model: [params: params])
        }
    }
}
