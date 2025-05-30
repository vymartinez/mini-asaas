package mini.asaas

import mini.asaas.dtos.CustomerDTO

class CustomerController {

    def customerService

    def save() {
        try {
            CustomerDTO customerDto = new CustomerDTO(params)
            Customer customer = customerService.save(customerDto)
            if (!customer) {
                render "Não foi possível salvar o cliente"
                return
            }
            redirect(action: "show", id: customer.id)
        } catch (Exception e) {
            render "Não foi possível salvar"
        }
    }

    def show() {
        try {
            Customer customer = Customer.get(params.id)
            if (!customer) {
                render "Cliente não encontrado"
            }
            return [customer: customer]
        } catch (Exception e) {
            render "Cliente não encontrado"
        }


    }
}