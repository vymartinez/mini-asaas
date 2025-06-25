package mini.asaas.user

import grails.validation.ValidationException

import mini.asaas.enums.MessageType
import mini.asaas.adapters.user.SaveUserAdapter

class UserController extends BaseController {

    UserService userService

    def save() {
        try {
            SaveUserAdapter adapter = new SaveUserAdapter(params)

            User user = userService.create(adapter)
            buildFlashAlert("Conta criada com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'dashboard', action: 'index')
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(controller: 'onboarding', action: 'createCustomer', params: params)
        }
    }

    def update() {
        try {
            SaveUserAdapter adapter = new SaveUserAdapter(params)
            User currentUser = getCurrentUser()

            User user = userService.update(adapter, currentUser)
            buildFlashAlert("Usuário atualizado com sucesso!", MessageType.SUCCESS, true)
            redirect(controller: 'dashboard', action: 'profile')
        } catch (ValidationException validationException) {
            buildFlashAlert(validationException.errors.allErrors.defaultMessage.join(", "), MessageType.ERROR, false)
            redirect(controller: 'dashboard', action: 'profile', params: params)
        } catch (RuntimeException exception) {
            buildFlashAlert(exception.message, MessageType.ERROR, false)
            redirect(controller: 'dashboard', action: 'index')
        }
    }
}