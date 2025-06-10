package mini.asaas.adapters

class saveCustomerAdapter {

    String name

    String email

    String cpfCnpj

    AddressAdapter address

    public saveCustomerAdapter(Map params) {
        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.address = new AddressAdapter(params)
    }
}
