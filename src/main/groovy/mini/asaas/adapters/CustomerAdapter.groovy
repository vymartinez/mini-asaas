package mini.asaas.adapters

class CustomerAdapter {

    String name

    String email

    String cpfCnpj

    AddressAdapter address

    public CustomerAdapter(Map params) {
        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.address = new AddressAdapter(params)
    }
}
