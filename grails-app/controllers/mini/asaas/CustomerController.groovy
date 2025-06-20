package mini.asaas

import mini.asaas.adapters.SaveCustomerAdapter
import mini.asaas.enums.MessageType
import grails.compiler.GrailsCompileStatic
import grails.validation.ValidationException

@GrailsCompileStatic
class CustomerController extends BaseController {

    CustomerService customerService

    def create() {
        try {
            SaveCustomerAdapter saveCustomerAdapter = new SaveCustomerAdapter(params)

            Customer customer = customerService.create(saveCustomerAdapter)

            buildFlashAlert("Cadastro realizado com sucesso!", MessageType.SUCCESS, true)
            redirect(url: '/dashboard', model: [customer: customer])
        } catch (ValidationException validationException) {
            buildFlashAlert("Atenção: " + validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(url: '/onboarding/createCustomer', model: [params: params])
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/onboarding/createCustomer', model: [params: params])
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
        } catch (RuntimeException runtimeException) {
            buildFlashAlert(runtimeException.getMessage(), MessageType.ERROR, false)
            redirect(url: '/dashboard/profile', model: [params: params])
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
            redirect(url: '/dashboard')
        }
    }
}
