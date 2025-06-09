package mini.asaas

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import mini.asaas.adapters.CustomerAdapter
import mini.asaas.utils.CpfCnpjUtils
import mini.asaas.utils.DomainUtils
import mini.asaas.utils.Utils

@Transactional
class CustomerService {

    Customer create(CustomerAdapter customerAdapter) {

        Customer customer = validateBeforeSave(customerAdapter)
        if (customer.hasErrors()) {
            throw new ValidationException("Erro ao criar conta do usuário", customer.errors)
        }

        Address address = new Address(
            address: customerAdapter.address.address,
            addressNumber: customerAdapter.address.addressNumber,
            city: City.get(customerAdapter.address.cityId),
            zipCode: customerAdapter.address.zipCode,
            province: customerAdapter.address.province,
            complement: customerAdapter.address.complement
        )

        Customer validatedCustomer = new Customer(
            name: customerAdapter.name,
            email: customerAdapter.email,
            cpfCnpj: customerAdapter.cpfCnpj,
            personType: CpfCnpjUtils.getPersonType(customerAdapter.cpfCnpj),
            address: address
        )

        validatedCustomer.save(failOnError: true)
        return validatedCustomer
    }

    private Customer validateBeforeSave(CustomerAdapter customerAdapter) {
        Customer customer = new Customer();

        if (!customerAdapter.name) {
            DomainUtils.addError(customer, "O nome é obrigatório")
        }

        if (!customerAdapter.email) {
            DomainUtils.addError(customer, "O email é obrigatório")
        }

        if (customerAdapter.email && !Utils.emailIsValid(customerAdapter.email)) {
            DomainUtils.addError(customer, "O email informado é inválido")
        }

        if (!customerAdapter.cpfCnpj) {
            DomainUtils.addError(customer, "O CPF/CNPJ é obrigatório")
        }

        if (customerAdapter.cpfCnpj && !CpfCnpjUtils.validate(customerAdapter.cpfCnpj)) {
            DomainUtils.addError(customer, "O CPF/CNPJ informado não é válido")
        }

        Utils.validateAddress(customerAdapter.address, customer)

        return customer
    }
}
