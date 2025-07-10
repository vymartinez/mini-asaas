package mini.asaas.notification

import mini.asaas.Notification
import grails.compiler.GrailsCompileStatic
import grails.plugins.mail.MailService
import grails.gorm.transactions.Transactional
import mini.asaas.payment.Payment
import org.springframework.context.MessageSource
import mini.asaas.utils.BigDecimalUtils

@GrailsCompileStatic
@Transactional
class EmailNotificationService {

    MailService mailService

    public void send(Notification notification) {
        mailService.sendMail {
            to notification.customer.email
            subject notification.subject
            body notification.body
        }
    }
}