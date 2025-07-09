package mini.asaas.notification

import mini.asaas.Customer
import mini.asaas.Notification
import mini.asaas.enums.NotificationType
import mini.asaas.payment.Payment
import mini.asaas.repositorys.NotificationRepository
import mini.asaas.utils.BigDecimalUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import org.springframework.context.MessageSource

@GrailsCompileStatic
@Transactional
class NotificationService {

    EmailNotificationService emailNotificationService
    MessageSource messageSource

    public Notification create(Object[] subjectArgs, Object[] bodyArgs, Customer customer, NotificationType type) {
        Notification notification = buildNotification(subjectArgs, bodyArgs, customer, type)

        notification.save(failOnError: true)

        emailNotificationService.send(notification)

        return notification
    }

    public List<Notification> list(Long customerId, Integer max, Integer offset) {
         return NotificationRepository.query([customerId: customerId]).readOnly().list([max: max, offset: offset])
    }

    public void notifyUpdated(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.arrendondarPadrao(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_UPDATED
        )
    }

    public void notifyDeleted(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.arrendondarPadrao(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_DELETED
        )
    }

    public void notifyRestored(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.arrendondarPadrao(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_RESTORED
        )
    }

    public void notifyConfirmedInCash(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.arrendondarPadrao(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_CONFIRMEDINCASH
        )
    }

    public void notifyExpired(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.arrendondarPadrao(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_EXPIRED
        )
    }

    private Notification buildNotification(Object[] subjectArgs, Object[] bodyArgs, Customer customer, NotificationType type) {
        Notification notification = new Notification()

        String[] types = type.toString().split("_")
        String status = types[1].toLowerCase()
        String domain = types[0].toLowerCase()

        String subject = messageSource.getMessage(
            domain + '.notify.' + status + '.subject',
            subjectArgs,
            Locale.getDefault()
        )

        String body = messageSource.getMessage(
            domain + '.notify.' + status + '.body',
            bodyArgs,
            Locale.getDefault()
        )

        notification.subject = subject
        notification.body = body
        notification.type = type
        notification.customer = customer

        return notification
    }
}
