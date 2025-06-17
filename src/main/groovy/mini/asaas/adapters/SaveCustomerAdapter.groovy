package mini.asaas.adapters

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SaveCustomerAdapter {

    String name

    String email

    String cpfCnpj

    AddressAdapter address

    public SaveCustomerAdapter(Map params) {
        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.address = new AddressAdapter(params)
    }
}
