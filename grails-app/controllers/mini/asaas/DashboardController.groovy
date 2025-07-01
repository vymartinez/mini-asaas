package mini.asaas

import grails.plugin.springsecurity.annotation.Secured
import mini.asaas.repositorys.PayerRepository

class DashboardController extends BaseController {

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index() {
        Long customerId = getCurrentCustomerId()
        List<Payer> payers = PayerRepository.query([customerId: customerId]).readOnly().list([max: 9, offset: 0])

        return [payers: payers]
    }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def profile() {
        Customer customer = getCurrentCustomer()
        return [customer: customer]
    }
}
