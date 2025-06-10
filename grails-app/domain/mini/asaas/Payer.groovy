package mini.asaas

import mini.asaas.utils.BaseEntity

class Payer extends BaseEntity {

    String name

    String email

    String cpfCnpj

    String cellPhone

    Address address

    Customer customer
}