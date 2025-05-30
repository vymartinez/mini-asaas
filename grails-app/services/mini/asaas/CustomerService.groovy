package mini.asaas

import grails.gorm.transactions.Transactional
import mini.asaas.dtos.CustomerDTO

@Transactional
class CustomerService {
    Customer save(CustomerDTO customerDto) {

        Address address = new Address(
            address: customerDto.address.address,
            addressNumber: customerDto.address.addressNumber,
            city: customerDto.address.city,
            zipCode: customerDto.address.zipCode,
            province: customerDto.address.province,
            complement: customerDto.address.complement
        )

        Customer customer = new Customer(
           name: customerDto.name,
           email: customerDto.email,
           cpfCnpj: customerDto.cpfCnpj,
           personType: customerDto.personType,
           address: address
        )

        customer.save(failOnError: true)
        return customer
    }
}
