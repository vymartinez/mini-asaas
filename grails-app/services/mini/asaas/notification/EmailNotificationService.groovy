package mini.asaas.notification

import mini.asaas.payment.Payment

import grails.plugins.mail.MailService
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.context.MessageSource

@CompileStatic
@Transactional
class EmailNotificationService {

    MessageSource messageSource

    CurrencyFormatterService currencyFormatterService

    MailService mailService

    public void notifyCreated(Payment payment) {
        String amount = currencyFormatterService.format(payment.value)

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
        String amount = currencyFormatterService.format(payment.value)

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
        String amount = currencyFormatterService.format(payment.value)

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
        String amount = currencyFormatterService.format(payment.value)

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

    private void send(Map<String, String> args) {
        mailService.sendMail {
            to args.get('to')
            subject args.get('subject')
            body args.get('body')
        }
    }
}