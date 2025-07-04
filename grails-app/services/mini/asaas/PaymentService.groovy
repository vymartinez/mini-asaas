package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.adapters.UpdatePaymentAdapter
import mini.asaas.user.User
import mini.asaas.Customer
import mini.asaas.Payer
import mini.asaas.payment.Payment
import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.enums.PaymentStatus
import mini.asaas.notification.EmailNotificationService
import mini.asaas.repositorys.PaymentRepository
import mini.asaas.repositorys.PayerRepository

import grails.validation.ValidationException
import mini.asaas.utils.user.UserUtils

@GrailsCompileStatic
@Transactional
class PaymentService {

    EmailNotificationService emailNotificationService
    PayerRepository payerRepository
    PaymentRepository paymentRepository

    public List<Payment> list(Map params, Integer max, Integer offset, Long customerId) {
        Long payerId = params.get("payerId")?.toString()?.toLong()

        Payer payer = findPayer(payerId, customerId)

        Map filters = buildListFilters(payer.id)

        return paymentRepository.query(filters).readOnly().list([max: max, offset: offset]).toList()
    }

    public Payment getById(Long id, Long payerId, Long customerId) {
        Payment payment = paymentRepository.query([id: id, payerId: payerId, customerId: customerId]).readOnly().get()

        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID $id")
        }

        return payment
    }

    public Payment create(SavePaymentAdapter adapter, Long customerId) {
        Long payerId = adapter.payerId?.toString()?.toLong()
        Payer payer = findPayer(payerId, customerId)

        Payment payment = new Payment()
        payment.payer = payer
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate
        payment.status = PaymentStatus.PENDING

        if (!payment.payer) {
            throw new IllegalArgumentException("Pagamento precisa estar vinculado a um payer.")
        }

        if (!payment.save()) {
            throw new ValidationException("Erro ao salvar pagamento", payment.errors)
        }

        emailNotificationService.notifyCreated(payment)

        return payment
    }

    public Payment update(UpdatePaymentAdapter adapter, Long customerId) {
        if (!adapter.id) {
            throw new RuntimeException("ID da cobrança é obrigatório para atualização")
        }

        Long payerId = adapter.payerId?.toString()?.toLong()
        Payment payment = getById(adapter.id, payerId, customerId)
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate

        if (!payment.payer) {
            throw new IllegalArgumentException("Pagamento precisa estar vinculado a um payer.")
        }

        if (!payment.save()) {
            throw new ValidationException("Erro ao atualizar pagamento", payment.errors)
        }

        return payment
    }

    public void delete(Long id, Long payerId, Long customerId) {
        Payment payment = getById(id, payerId, customerId)

        payment.deleted = true

        if (!payment.save()) {
            throw new ValidationException("Erro ao marcar o pagamento como deletado", payment.errors)
        }

        emailNotificationService.notifyDeleted(payment)
    }

    public Payment confirmCashPayment(Long id, Long payerId, Long customerId) {
        Payment payment = getById(id, payerId, customerId)

        if (payment.status != PaymentStatus.PENDING) {
            throw new RuntimeException("Pagamento não está pendente e não pode ser confirmado em dinheiro.")
        }

        if (!payment.payer) {
            throw new IllegalArgumentException("Pagamento precisa estar vinculado a um payer.")
        }

        payment.status = PaymentStatus.RECEIVED

        if (!payment.save()) {
            throw new ValidationException("Erro ao confirmar pagamento em dinheiro", payment.errors)
        }

        emailNotificationService.notifyPaid(payment)

        return payment
    }

    public void expirePayment(Long id, Long payerId, Long customerId) {
        Payment payment = getById(id, payerId, customerId)
        if (payment.status == PaymentStatus.PENDING && payment.dueDate.before(new Date())) {
            updateStatusAndNotify(payment, PaymentStatus.OVERDUE) { Payment expiredPayment ->
                emailNotificationService.notifyExpired(expiredPayment)
            }
        }
    }

    public void expireOverduePayments() {
        paymentRepository
                .query([status: PaymentStatus.PENDING])
                .list()
                .toList()
                .collect { it as Payment }
                .findAll { it.dueDate.before(new Date()) }
                .each { payment ->
                    updateStatusAndNotify(payment, PaymentStatus.OVERDUE, emailNotificationService.&notifyExpired)
                }
    }

    public Payment restore(Long id, Long payerId, Long customerId) {
        Payment payment = getById(id, payerId, customerId)

        if (!payment.deleted) {
            throw new RuntimeException("Pagamento não está excluído e não pode ser restaurado.")
        }

        payment.deleted = false

        payment.status = PaymentStatus.PENDING

        if (!payment.payer) {
            throw new ValidationException("Pagamento precisa estar vinculado a um payer", payment.errors)
        }

        if (!payment.save()) {
            throw new ValidationException("Erro ao restaurar pagamento", payment.errors)
        }

        emailNotificationService.notifyRestored(payment)

        return payment
    }

    private Payer findPayer(Long payerId, Long customerId) {

        Payer payer = payerRepository.query([id: payerId, customerId: customerId]).get()

        if (!payer) {
            throw new RuntimeException("Payer não encontrado para o Customer ${customerId}")
        }

        return payer
    }

    private Map buildListFilters(Long payerId) {
        return [ payerId: payerId, deleted: false ]
    }

    private void updateStatusAndNotify(Payment payment, PaymentStatus newStatus, Closure<? extends Void> notificationCallback) {
        payment.status = newStatus

        if (!payment.payer) {
            throw new IllegalArgumentException("Pagamento precisa estar vinculado a um payer.")
        }

        if (!payment.save()) {
            throw new ValidationException("Erro ao atualizar status do pagamento", payment.errors)
        }

        if (notificationCallback != null) {
            notificationCallback.call()
        }
    }
}