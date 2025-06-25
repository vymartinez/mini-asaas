package mini.asaas

import mini.asaas.adapters.SaveCustomerAdapter
import mini.asaas.enums.MessageType
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@GrailsCompileStatic
class CustomerController extends BaseController {

    CustomerService customerService

    @Secured("permitAll")
    def create() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Customer customer = customerService.create(saveCustomerAdapter)

            buildFlashAlert("Cadastro realizado com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/dashboard', model: [customer: customer])
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(controller: 'onboarding', action: 'createCustomer', params: params)
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(controller: 'onboarding', action: 'createCustomer', params: params)
        }
    }

    def update() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Long currentCustomerId = 1
            Customer customer = customerService.update(saveCustomerAdapter, currentCustomerId)

            buildFlashAlert("Dados alterados com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/dashboard', model: [customer: customer])
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(url: '/dashboard/profile', model: [params: params])
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/dashboard')
        }
    }
}
