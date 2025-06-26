package mini.asaas

import grails.plugin.springsecurity.annotation.Secured

class DashboardController extends BaseController {

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def index() { }

    @Secured(['IS_AUTHENTICATED_FULLY'])
    def profile() {
        Customer customer = getCurrentCustomer()
        return [customer: customer]
    }
}
