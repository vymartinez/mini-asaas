package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService

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

@GrailsCompileStatic
@Transactional
class PaymentService {

    SpringSecurityService springSecurityService
    PayerRepository payerRepository
    PaymentRepository paymentRepository
    EmailNotificationService emailNotificationService

    private Payer getCurrentPayer() {
        User user = springSecurityService.currentUser as User
        Customer customer = Customer.findWhere(user: user)
        if (!customer) {
            throw new RuntimeException("Customer não encontrado para o usuário ${user.username}")
        }
        Payer payer = payerRepository.query([customerId: customer.id]).get()
        if (!payer) {
            throw new RuntimeException("Payer não encontrado para o Customer ${customer.id}")
        }
        return payer
    }

    private Map buildListFilters(Map params, Long payerId) {
        return [ payerId: payerId ]
    }

    private void updateStatusAndNotify(Payment payment, PaymentStatus newStatus, Closure<? extends Void> notificationCallback) {
        payment.status = newStatus

        String failureMsg

        switch(newStatus) {
            case PaymentStatus.OVERDUE:
                failureMsg = "Erro ao vencer pagamento id: ${payment.id}"
                break
            case PaymentStatus.RECEIVED:
                failureMsg = "Erro ao confirmar pagamento em dinheiro id: ${payment.id}"
                break
            case PaymentStatus.PENDING:
                failureMsg = "Erro ao criar cobrança id: ${payment.id}"
                break
            default:
                failureMsg = "Falha ao atualizar status do pagamento id: ${payment.id}"
        }

        if (!payment.save()) {
            log.error(failureMsg)
        } else {
            notificationCallback(payment)
        }
    }

    @Transactional(readOnly = true)
    public List<Payment> list(Map params, Integer max, Integer offset) {
        Payer payer = getCurrentPayer()
        Map filters = buildListFilters(params, payer.id)
        paymentRepository.query(filters).list([max: max, offset: offset])
    }

    @Transactional(readOnly = true)
    public Long count(Map params) {
        Payer payer = getCurrentPayer()
        Map filters = buildListFilters(params, payer.id)
        paymentRepository.query(filters).count()
    }

    @Transactional(readOnly = true)
    public Payment getById(Long id) {
        Payment payment = paymentRepository.query([id: id]).get()
        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado para ID $id")
        }
        if (payment.payer.id != getCurrentPayer().id) {
            throw new RuntimeException("Acesso negado: você não é o dono deste pagamento.")
        }
        return payment
    }

    public Payment create(SavePaymentAdapter adapter) {
        Payer payer = getCurrentPayer()
        Payment payment = new Payment()
        payment.payer = payer
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate
        payment.status = PaymentStatus.PENDING

        if (!payment.save()) {
            throw new ValidationException("Erro ao salvar pagamento", payment.errors)
        }

        emailNotificationService.notifyCreated(payment)
        return payment
    }

    public Payment update(SavePaymentAdapter adapter) {
        if (!adapter.id) {
            throw new RuntimeException("ID da cobrança é obrigatório para atualização")
        }

        Payment payment = getById(adapter.id)
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate

        if (!payment.save()) {
            throw new ValidationException("Erro ao atualizar pagamento", payment.errors)
        }

        return payment
    }

    public void delete(Long id) {
        Payment payment = getById(id)
        emailNotificationService.notifyDeleted(payment)
        payment.delete()
    }

    public Payment confirmCashPayment(Long id) {
        Payment payment = getById(id)
        if (payment.status != PaymentStatus.PENDING) {
            throw new RuntimeException("Pagamento não está pendente e não pode ser confirmado em dinheiro.")
        }
        payment.status = PaymentStatus.RECEIVED

        if (!payment.save()) {
            throw new ValidationException("Erro ao confirmar pagamento em dinheiro", payment.errors)
        }
        emailNotificationService.notifyPaid(payment)
        return payment
    }

    public void expirePayment(Long id) {
        Payment payment = getById(id)
        if (payment.status == PaymentStatus.PENDING && payment.dueDate.before(new Date())) {
            updateStatusAndNotify(payment, PaymentStatus.OVERDUE) { Payment expiredPayment ->
                emailNotificationService.notifyExpired(expiredPayment)
            }
        }
    }

    public void expireOverduePayments() {
        Date now = new Date()

        paymentRepository
                .query([status: PaymentStatus.PENDING])
                .list() as List<Payment>
                .findAll { it.dueDate.before(now) }
                .each { payment ->
                    updateStatusAndNotify(payment, PaymentStatus.OVERDUE, emailNotificationService.&notifyExpired)
                }
    }

    public Payment restore(Long id) {
        Payment payment = getById(id)
        if (!payment.deleted) {
            throw new RuntimeException("Pagamento não está excluído e não pode ser restaurado.")
        }
        payment.deleted = false
        payment.status = PaymentStatus.PENDING
        if (!payment.save()) {
            throw new ValidationException("Erro ao restaurar pagamento", payment.errors)
        }
        emailNotificationService.notifyRestored(payment)
        return payment
    }
}