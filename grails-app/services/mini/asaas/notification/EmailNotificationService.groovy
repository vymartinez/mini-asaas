package mini.asaas.notification

import mini.asaas.Notification
import grails.compiler.GrailsCompileStatic
import grails.plugins.mail.MailService
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class EmailNotificationService {

    MailService mailService

    public void sendEmail(Notification notification) {
        send(to: notification.customer.email, subject: notification.subject, body: notification.body)
    }

    private void send(Map<String, String> args) {
        mailService.sendMail {
            to args.get('to')
            subject args.get('subject')
            body args.get('body')
        }
    }
}