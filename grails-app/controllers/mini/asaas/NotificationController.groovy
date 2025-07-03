package mini.asaas

import mini.asaas.enums.MessageType
import mini.asaas.notification.NotificationService
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class NotificationController extends BaseController {

    NotificationService notificationService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list() {
        try {
            Long currentCustomerId = getCurrentCustomerId()
            List<Notification> notifications = notificationService.list(currentCustomerId, getLimitPerPage(), getOffset())

            render(template: '/templates/notification/center', model: [notifications: notifications])
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao listar as notificações. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
        }
    }
}
