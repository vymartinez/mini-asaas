package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
@Secured(['IS_AUTHENTICATED_FULLY'])
class DashboardController extends BaseController {

    def index() { }

    def profile() {
        Customer customer = getCurrentCustomer()
        return [customer: customer]
    }
}
