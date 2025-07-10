package mini.asaas.repositorys

import grails.compiler.GrailsCompileStatic
import mini.asaas.payment.Payment
import mini.asaas.enums.BillingType
import mini.asaas.enums.PaymentStatus
import org.grails.datastore.mapping.query.api.BuildableCriteria
import infrastructure.repository.Repository

@GrailsCompileStatic
class PaymentRepository implements Repository<Payment, PaymentRepository> {

    @Override
    BuildableCriteria getBuildableCriteria() {
        Payment.createCriteria()
    }

    @Override
    public void buildCriteria() {
        addCriteria {

            if (search.containsKey('payer.customer.id')) {
                createAlias("payer", "payer")
                createAlias("payer.customer", "customer")
            }

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

            if (search.containsKey('payer.customer.id')) {
                eq('customer.id', search['payer.customer.id'] as Long)
            }

            if (search.containsKey('payer.name[like]')) {
                like('payer.name', "%${search['payer.name[like]']}%")
            }
        }
    }

    @Override
    List<String> listAllowedFilters() {
        [
                'payerId',
                'billingType',
                'status',
                'dueDate',
                'customerId',
                'payer.customer.id',
                'payer.name[like]',
                'dueDate[between]',
                'dueDate[lt]'
        ]
    }

    @Override
    Boolean getDomainHasSoftDelete() {
        return true
    }
}