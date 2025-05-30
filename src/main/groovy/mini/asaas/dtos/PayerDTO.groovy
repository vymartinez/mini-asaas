package mini.asaas.dtos

class PayerDTO {
    String name
    String email
    String cpfCnpj
    String cellphone
    Long customerId
    AddressDTO address

    PayerDTO(Map params) {
        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.cellphone = params.cellphone
        // this.customerId = (usuário logado?)
        this.address = new AddressDTO(params)
    }
}

