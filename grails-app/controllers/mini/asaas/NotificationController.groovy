package mini.asaas

import mini.asaas.enums.MessageType
import mini.asaas.notification.NotificationService

import grails.compiler.GrailsCompileStatic
import grails.gorm.PagedResultList
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class NotificationController extends BaseController {

    NotificationService notificationService

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def list() {
        try {
            Integer max = getLimitPerPage()
            Integer offset = getOffset()
            Long currentCustomerId = getCurrentCustomerId()
            PagedResultList<Notification> notifications = notificationService.list(currentCustomerId, max, offset)

            render(template: '/templates/notification/group', model: [notifications: notifications, totalCount: notifications.totalCount, max: max], params: params)
        } catch (Exception exception) {
            buildFlashAlert("Ocorreu um erro ao listar as notificações. Por favor, tente novamente mais tarde.", MessageType.ERROR, false)
        }
    }
}
