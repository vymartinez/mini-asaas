package mini.asaas.notification

import mini.asaas.Customer
import mini.asaas.Notification
import mini.asaas.enums.NotificationStatus
import mini.asaas.payment.Payment
import mini.asaas.repositorys.NotificationRepository
import mini.asaas.utils.BigDecimalUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import org.springframework.context.MessageSource

import java.math.RoundingMode

@GrailsCompileStatic
@Transactional
class NotificationService {

    EmailNotificationService emailNotificationService
    MessageSource messageSource

    public Notification create(Payment payment, Customer customer, NotificationStatus notificationStatus) {

        if (alreadySent(payment.id, customer.id, notificationStatus)) throw new RuntimeException("Notificação já enviada.")

        Notification notification = buildNotification(payment, customer, notificationStatus)

        notification.save(failOnError: true)

        emailNotificationService.send(notification)

        return notification
    }

    public List<Notification> list(Long customerId, Integer max, Integer offset) {
         return NotificationRepository.query([customerId: customerId]).readOnly().list([max: max, offset: offset])
    }

    private Notification buildNotification(Payment payment, Customer customer, NotificationStatus notificationStatus) {
        Notification notification = new Notification()

        String amount = BigDecimalUtils.round(payment.value, 2, RoundingMode.HALF_UP).toString()
        String status = notificationStatus.toString().toLowerCase()

        String subject = messageSource.getMessage(
            'payment.notify.' + status + '.subject',
            [payment.id] as Object[],
            Locale.getDefault()
        )

        String body = messageSource.getMessage(
            'payment.notify.' + status + '.body',
            [amount, payment.payer.name] as Object[],
            Locale.getDefault()
        )

        notification.subject = subject
        notification.body = body
        notification.notificationStatus = notificationStatus
        notification.customer = customer
        notification.payment = payment

        return notification
    }

    private Boolean alreadySent(Long paymentId, Long customerId, NotificationStatus status) {
        return (NotificationRepository.query([paymentId: paymentId, customerId: customerId, status: status]).readOnly().get() != null)
    }
}
