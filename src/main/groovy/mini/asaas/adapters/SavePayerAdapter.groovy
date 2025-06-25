package mini.asaas.adapters

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SavePayerAdapter {

    String name

    String email

    String cpfCnpj

    AddressAdapter address

    String cellPhone

    public SavePayerAdapter(Map params) {
        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.address = new AddressAdapter(params)
        this.cellPhone = params.cellPhone
    }
}
