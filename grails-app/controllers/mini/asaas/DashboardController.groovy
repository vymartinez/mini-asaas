package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured
import mini.asaas.repositorys.PayerRepository

@GrailsCompileStatic
@Secured(['IS_AUTHENTICATED_FULLY'])
class DashboardController extends BaseController {

    def index() {
        Long customerId = getCurrentCustomerId()
        List<Payer> payers = PayerRepository.query([customerId: customerId]).readOnly().list([max: 9, offset: 0])

        return [payers: payers]
    }

    def profile() {
        Customer customer = getCurrentCustomer()
        return [customer: customer]
    }
}
