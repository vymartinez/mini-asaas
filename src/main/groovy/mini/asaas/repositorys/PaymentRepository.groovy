package mini.asaas.repositorys

import grails.compiler.GrailsCompileStatic
import mini.asaas.payment.Payment
import org.grails.datastore.mapping.query.api.BuildableCriteria
import infrastructure.repository.Repository
import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus

@GrailsCompileStatic
class PaymentRepository implements Repository<Payment, PaymentRepository> {

    Boolean domainHasSoftDelete = true

    @Override
    public BuildableCriteria getBuildableCriteria() {
        Payment.createCriteria()
    }

    @Override
    public void buildCriteria() {
        addCriteria {
            if (search.containsKey('payerId')) {
                eq('payer.id', search.payerId as Long)
            }
            if (search.containsKey('billingType')) {
                eq('billingType', search.billingType as BillingType)
            }
            if (search.containsKey('status')) {
                eq('status', search.status as PaymentStatus)
            }
            if (search.containsKey('dueDate')) {
                le('dueDate', search.dueDate as Date)
            }
        }
    }

    @Override
    public List<String> listAllowedFilters() {
        ['payerId', 'billingType', 'status', 'dueDate']
    }
}