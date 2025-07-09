package mini.asaas.notification

import mini.asaas.Notification
import grails.compiler.GrailsCompileStatic
import grails.plugins.mail.MailService
import grails.gorm.transactions.Transactional
import mini.asaas.payment.Payment
import org.springframework.context.MessageSource

@GrailsCompileStatic
@Transactional
class EmailNotificationService {

    MailService mailService

    MessageSource messageSource

    public void notifyCreated(Payment payment) {
        String amount = BigDecimalUtils.round(payment.value, 2, RoundingMode.HALF_UP).toString()

        String subject = messageSource.getMessage(
                'payment.notify.created.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'payment.notify.created.body',
                [amount, payment.payer.name] as Object[],
                Locale.getDefault()
        )

        send(to: payment.payer.email, subject: subject, body: body)
    }

    public void notifyPaid(Payment payment) {
        String amount = BigDecimalUtils.round(payment.value, 2, RoundingMode.HALF_UP).toString()

        String subject = messageSource.getMessage(
                'payment.notify.paid.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'payment.notify.paid.body',
                [amount, payment.payer.name] as Object[],
                Locale.getDefault()
        )

        send(to: payment.payer.email, subject: subject, body: body)
    }

    public void notifyExpired(Payment payment) {
        String amount = BigDecimalUtils.round(payment.value, 2, RoundingMode.HALF_UP).toString()

        String subject = messageSource.getMessage(
                'payment.notify.expired.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'payment.notify.expired.body',
                [amount, payment.payer.name, payment.dueDate?.toString()] as Object[],
                Locale.getDefault()
        )

        send(to: payment.payer.email, subject: subject, body: body)
    }

    public void notifyDeleted(Payment payment) {
        String amount = BigDecimalUtils.round(payment.value, 2, RoundingMode.HALF_UP).toString()

        String subject = messageSource.getMessage(
                'payment.notify.deleted.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'payment.notify.deleted.body',
                [amount, payment.payer.name] as Object[],
                Locale.getDefault()
        )

        send(to: payment.payer.email, subject: subject, body: body)
    }

    public void notifyRestored(Payment payment) {
        String amount = BigDecimalUtils.round(payment.value, 2, RoundingMode.HALF_UP).toString()
        String subject = messageSource.getMessage(
                'payment.notify.restored.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )
        String body = messageSource.getMessage(
                'payment.notify.restored.body',
                [amount, payment.payer.name] as Object[],
                Locale.getDefault()
        )
        send(to: payment.payer.email, subject: subject, body: body)
    }

    public void notifyConfirmedInCash(Payment payment) {
        String amount = BigDecimalUtils.round(payment.value, 2, RoundingMode.HALF_UP).toString()

        String subject = messageSource.getMessage(
                'payment.notify.confirmedInCash.subject',
                [payment.id] as Object[],
                Locale.getDefault()
        )

        String body = messageSource.getMessage(
                'payment.notify.confirmedInCash.body',
                [amount, payment.payer.name] as Object[],
                Locale.getDefault()
        )

        send(to: payment.payer.email, subject: subject, body: body)
    }

    private void send(Map<String, String> args) {

        mailService.sendMail {
            to args.get('to')
            subject args.get('subject')
            body args.get('body')
        }
    }
}