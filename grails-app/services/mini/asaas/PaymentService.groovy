package mini.asaas

import grails.gorm.PagedResultList
import grails.validation.ValidationException
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

import mini.asaas.adapters.SavePaymentAdapter
import mini.asaas.adapters.UpdatePaymentAdapter


import mini.asaas.enums.PaymentStatus
import mini.asaas.notification.EmailNotificationService
import mini.asaas.PayerService
import mini.asaas.payment.Payment
import mini.asaas.repositorys.PaymentRepository
import mini.asaas.utils.DomainUtils


@GrailsCompileStatic
@Transactional
class PaymentService {

    EmailNotificationService emailNotificationService
    PayerService payerService
    PaymentRepository paymentRepository

    public Payment create(SavePaymentAdapter savePaymentAdapter, Long customerId) {

        Payment payment = validate(savePaymentAdapter)

        if (payment.hasErrors()) {
            throw new ValidationException("Erro ao criar pagamento", payment.errors)
        }

        Payer payer = payerService.findById(savePaymentAdapter.payerId, customerId)
        buildPayment(payment, savePaymentAdapter, payer)

        payment.save(failOnError: true)
        emailNotificationService.notifyCreated(payment)
        return payment
    }

    public PagedResultList<Payment> list(Map params, Long customerId, Integer max, Integer offset) {

        Map filters = buildListFilters(params, customerId)

        if (params.showDeleted in [true, 'true', 'on']) {
            filters.includeDeleted = true
        }

        return paymentRepository.query(filters)
                .readOnly()
                .list([max: max, offset: offset])
    }

    public Payment update(UpdatePaymentAdapter adapter, Long customerId) {
        Payment payment = findById(adapter.id, customerId)

        Payer payer = payerService.findById(adapter.payerId, customerId)
        buildPayment(payment, adapter, payer)

        if (payment.hasErrors()) {
            throw new ValidationException("Erro ao atualizar pagamento", payment.errors)
        }

        payment.save(failOnError: true)

        if (payment.status == PaymentStatus.RECEIVED) {
            emailNotificationService.notifyPaid(payment)
        }

        return payment
    }

    public void disable(Long paymentId, Long customerId) {
        Payment payment = findById(paymentId, customerId)

        payment.deleted = true
        payment.save(failOnError: true)
        emailNotificationService.notifyDeleted(payment)
    }

    public void restore(Long paymentId, Long customerId) {
        Payment payment = findById(paymentId, customerId)

        payment.deleted = false
        payment.save(failOnError: true)
        emailNotificationService.notifyRestored(payment)
    }

    public void notifyOverduePayments() {
        List<Payment> overdue = paymentRepository
                .query([
                        status: PaymentStatus.PENDING,
                        dueDate: new Date()
                ])
                .list()

        overdue.each { emailNotificationService.notifyExpired(it) }
    }

    public Payment findById(Long paymentId, Long customerId) {

        Payment payment = paymentRepository.query([id: paymentId, 'payer.customer.id': customerId, includeDeleted: true]).get()

        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado")
        }

        return payment
    }


    private Payment validate(SavePaymentAdapter adapter) {

        Payment payment = new Payment()

        if (!adapter.payerId) {
            DomainUtils.addError(payment, "O payerId é obrigatório")
        }

        if (!adapter.billingType) {
            DomainUtils.addError(payment, "O tipo de cobrança é obrigatório")
        }

        if (!adapter.value) {
            DomainUtils.addError(payment, "O valor é obrigatório")
        }

        return payment
    }

    private Payment validate(UpdatePaymentAdapter adapter) {

        Payment payment = new Payment()

        if (!adapter.payerId) {
            DomainUtils.addError(payment, "O payerId é obrigatório")
        }

        if (!adapter.billingType) {
            DomainUtils.addError(payment, "O tipo de cobrança é obrigatório")
        }

        if (!adapter.value) {
            DomainUtils.addError(payment, "O valor é obrigatório")
        }

        if (!adapter.dueDate){
            DomainUtils.addError(payment, "A data de vencimento é obrigatório")
        }

        return payment
    }

    private void buildPayment(Payment payment, SavePaymentAdapter adapter, Payer payer) {
        payment.payer = payer
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate
        payment.status = PaymentStatus.PENDING
    }

    private void buildPayment(Payment payment, UpdatePaymentAdapter adapter, Payer payer) {
        payment.payer = payer
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.dueDate = adapter.dueDate
        payment.status = adapter.status
    }

    private Map buildListFilters(Map params, Long customerId) {
        Map filters = [:]

        if (params.payerId) {
            filters.payerId = params.payerId as Long
        }

        if (params."payer.name[like]") {
            filters."payer.name[like]" = params."payer.name[like]"
        }

        filters.'payer.customer.id' = customerId

        filters.includeDeleted = true

        return filters
    }
}