package mini.asaas.notification

import mini.asaas.Customer
import mini.asaas.Notification
import mini.asaas.enums.NotificationType
import mini.asaas.payment.Payment
import mini.asaas.repositorys.NotificationRepository
import mini.asaas.utils.BigDecimalUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.PagedResultList
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

    public PagedResultList<Notification> list(Long customerId, Integer max, Integer offset) {
         return NotificationRepository.query([customerId: customerId]).readOnly().list([max: max, offset: offset])
    }

    public notifyPaymentCreated(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.roundDefault(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_CREATED
        )
    }

    public void notifyPaymentPaid(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.roundDefault(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_PAID
        )
    }

    public void notifyPaymentUpdated(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.roundDefault(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_UPDATED
        )
    }

    public void notifyPaymentDeleted(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.roundDefault(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_DELETED
        )
    }

    public void notifyPaymentRestored(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.roundDefault(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_RESTORED
        )
    }

    public void notifyPaymentConfirmedInCash(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.roundDefault(payment.value).toString(), payment.payer.name] as Object[],
                payment.payer.customer,
                NotificationType.PAYMENT_CONFIRMEDINCASH
        )
    }

    public void notifyPaymentExpired(Payment payment) {
        create(
                [payment.id] as Object[],
                [BigDecimalUtils.roundDefault(payment.value).toString(), payment.payer.name] as Object[],
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
