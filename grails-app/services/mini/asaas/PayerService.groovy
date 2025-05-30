package mini.asaas

import grails.gorm.transactions.Transactional
import mini.asaas.dtos.PayerDTO

@Transactional
class PayerService {
    public Payer save(PayerDTO payerDto) {

        Address address = new Address(
            address: payerDto.address.address,
            addressNumber: payerDto.address.addressNumber,
            city: payerDto.address.city,
            zipCode: payerDto.address.zipCode,
            province: payerDto.address.province,
            complement: payerDto.address.complement
        )

        Payer payer = new Payer(
            name: payerDto.name,
            email: payerDto.email,
            cpfCnpj: payerDto.cpfCnpj,
            cellphone: payerDto.cellphone,
            customer: Customer.get(payerDto.customerId),
            address: address
        )

        payer.save(failOnError: true)
        return payer
    }
}