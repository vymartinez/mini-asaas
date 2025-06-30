package mini.asaas.adapters.user

import grails.compiler.GrailsCompileStatic
import mini.asaas.adapters.SaveCustomerAdapter

@GrailsCompileStatic
class SaveUserAdapter {

    String username

    String password

    SaveCustomerAdapter customer

    public SaveUserAdapter(Map params) {
        this.username = params.email
        this.password = params.password
        this.customer = new SaveCustomerAdapter(params)
    }
}
