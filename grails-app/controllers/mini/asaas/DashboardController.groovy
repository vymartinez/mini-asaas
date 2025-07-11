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
        Map pagination = [max: 9, offset: 0]
        List<Payer> payers = PayerRepository.query([customerId: customerId]).readOnly().list(pagination)
        List<Payment> payments = PaymentRepository.query(["payer.customer.id": customerId]).readOnly().list(pagination)

        return [payers: payers, payments: payments]
    }

    def profile() {
        Customer customer = getCurrentCustomer()
        return [customer: customer]
    }
}
