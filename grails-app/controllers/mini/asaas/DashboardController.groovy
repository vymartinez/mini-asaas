package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import mini.asaas.payment.Payment
import mini.asaas.repositorys.PayerRepository
import mini.asaas.repositorys.PaymentRepository

@GrailsCompileStatic
@Secured(['IS_AUTHENTICATED_FULLY'])
class DashboardController extends BaseController {

    def index() {
        Long customerId = getCurrentCustomerId()
        Integer max = 9
        Integer offset = 0
        List<Payer> payers = PayerRepository.query([customerId: customerId]).readOnly().list([max: max, offset: offset])
        List<Payment> payments = PaymentRepository.query(["payer.customer.id": customerId]).readOnly().list([max: max, offset: offset])

        return [payers: payers, payments: payments]
    }

    def profile() {
        Customer customer = getCurrentCustomer()
        return [customer: customer]
    }
}
