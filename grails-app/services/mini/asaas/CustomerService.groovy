package mini.asaas

import mini.asaas.adapters.SaveCustomerAdapter
import mini.asaas.utils.CpfCnpjUtils
import mini.asaas.utils.DomainUtils
import mini.asaas.utils.Utils

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
class CustomerService {

    public Customer create(SaveCustomerAdapter saveCustomerAdapter) {

        Customer customer = validate(saveCustomerAdapter)
        if (customer.hasErrors()) {
            throw new ValidationException("Erro ao criar conta do usuário", customer.errors)
        }

        Address address = new Address(
            address: saveCustomerAdapter.address.address,
            addressNumber: saveCustomerAdapter.address.addressNumber,
            city: City.get(saveCustomerAdapter.address.cityId),
            zipCode: saveCustomerAdapter.address.zipCode,
            province: saveCustomerAdapter.address.province,
            complement: saveCustomerAdapter.address.complement
        )

        Customer validatedCustomer = new Customer(
            name: saveCustomerAdapter.name,
            email: saveCustomerAdapter.email,
            cpfCnpj: saveCustomerAdapter.cpfCnpj,
            personType: CpfCnpjUtils.getPersonType(saveCustomerAdapter.cpfCnpj),
            address: address
        )

        validatedCustomer.save(failOnError: true)
        return validatedCustomer
    }

    private Customer validate(SaveCustomerAdapter saveCustomerAdapter) {
        Customer customer = new Customer()

        if (!saveCustomerAdapter.name) DomainUtils.addError(customer, "O nome é obrigatório")

        if (!saveCustomerAdapter.email) DomainUtils.addError(customer, "O email é obrigatório")

        if (saveCustomerAdapter.email && !Utils.emailIsValid(saveCustomerAdapter.email)) DomainUtils.addError(customer, "O email informado é inválido")

        if (!saveCustomerAdapter.cpfCnpj) DomainUtils.addError(customer, "O CPF/CNPJ é obrigatório")

        if (saveCustomerAdapter.cpfCnpj && !CpfCnpjUtils.validate(saveCustomerAdapter.cpfCnpj)) DomainUtils.addError(customer, "O CPF/CNPJ informado não é válido")

        return Utils.validateAddress(saveCustomerAdapter.address, customer)
    }
}
