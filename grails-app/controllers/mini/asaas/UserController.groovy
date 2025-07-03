package mini.asaas

import mini.asaas.enums.MessageType
import mini.asaas.adapters.user.SaveUserAdapter
import mini.asaas.user.User
import mini.asaas.user.UserService

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

@GrailsCompileStatic
class UserController extends BaseController {

    UserService userService

    @Secured("permitAll")
    def create() {
        try {
            SaveUserAdapter adapter = new SaveUserAdapter(params)

            userService.create(adapter)

            buildFlashAlert("Conta criada com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'dashboard', action: 'index')
        } catch (ValidationException validationException) {
            buildFlashAlert(validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(controller: 'onboarding', action: 'createCustomer', params: params)
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro interno ao criar a conta.", MessageType.ERROR, false)
            redirect(controller: 'onboarding', action: 'createCustomer', params: params)
        }
    }

    @Secured("IS_AUTHENTICATED_FULLY")
    def update() {
        try {
            SaveUserAdapter adapter = new SaveUserAdapter(params)
            User currentUser = getCurrentUser()

            userService.update(adapter, currentUser)

            buildFlashAlert("Usuário atualizado com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'dashboard', action: 'index')
        } catch (ValidationException validationException) {
            buildFlashAlert(validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(controller: 'dashboard', action: 'profile', params: params)
        } catch (RuntimeException exception) {
            buildFlashAlert("Ocorreu um erro interno ao atualizar a conta.", MessageType.ERROR, false)
            redirect(controller: 'dashboard', action: 'index')
        }
    }
}