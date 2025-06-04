package mini.asaas

import mini.asaas.utils.BaseEntity
import mini.asaas.enums.PersonType

class Customer extends BaseEntity {
    String name
    String email
    String cpfCnpj
    Address address
    PersonType personType
}