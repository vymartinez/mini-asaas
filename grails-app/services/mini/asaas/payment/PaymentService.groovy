package mini.asaas.payment

import mini.asaas.enums.PaymentStatus
import mini.asaas.notification.EmailNotificationService

import grails.gorm.transactions.Transactional

@Transactional
class PaymentService {
    EmailNotificationService emailNotificationService

    public Payment create(Map params) {
        Payment payment = new Payment(params)
        payment.status = PaymentStatus.PENDING
        payment.save(flush: true)
        emailNotificationService.notifyCreated(payment)
        return payment
    }

    public Payment update(Long id, Map params) {
        Payment payment = Payment.get(id)

        if (!payment) {
            throw new NoSuchElementException("Pagamento não encontrado para ID ${id}")
        }

        payment.properties = params
        payment.save(flush: true)
        return payment
    }

    public void softDelete(Long id) {
        Payment payment = Payment.get(id)

        if (payment) {
            payment.delete(flush: true)
            emailNotificationService.notifyDeleted(payment)
        }
    }

    public void restore(Long id) {
        Payment.where { id == id }.updateAll(deleted: false)
    }

    public List<Payment> list() {
        Payment.list()
    }

    public Payment get(Long id) {
        Payment.get(id)
    }

    public void confirm(Long id) {
        Payment payment = get(id)

        if (!payment) {
            throw new NoSuchElementException("Pagamento não encontrado para ID ${id}")
        }

        if (payment.status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Só é possível confirmar pagamentos pendentes (PENDING)")
        }

        payment.status = PaymentStatus.RECEIVED
        payment.save(flush: true)
        emailNotificationService.notifyPaid(payment)
    }
}
