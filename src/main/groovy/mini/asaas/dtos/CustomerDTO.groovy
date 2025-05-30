package mini.asaas.dtos;

import mini.asaas.enums.PersonType;

class CustomerDTO {
    String name
    String email
    String cpfCnpj
    PersonType personType
    AddressDTO address

    CustomerDTO(Map params) {
        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.personType = cpfCnpj.length() == 14 ? PersonType.LEGAL : PersonType.NATURAL
        this.address = new AddressDTO(params)
    }
}
