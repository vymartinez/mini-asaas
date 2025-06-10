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

    public Customer findById(Long customerId) {
        Customer customer = Customer.get(customerId)

        if (!customer) throw new RuntimeException("Usuário não encontrado!")

        return customer
    }

    public Customer update(SaveCustomerAdapter saveCustomerAdapter, Long customerId) {
        Customer toValidateCustomer = validate(saveCustomerAdapter)

        if (toValidateCustomer.hasErrors()) throw new ValidationException("Erro ao atualizar a conta do usuário", toValidateCustomer.errors)

        Customer customer = findById(customerId)

        Address address = new Address(
            address: saveCustomerAdapter.address.address,
            addressNumber: saveCustomerAdapter.address.addressNumber,
            city: City.get(saveCustomerAdapter.address.cityId),
            zipCode: saveCustomerAdapter.address.zipCode,
            province: saveCustomerAdapter.address.province,
            complement: saveCustomerAdapter.address.complement
        )

        customer.name = saveCustomerAdapter.name
        customer.email = saveCustomerAdapter.email
        customer.cpfCnpj = saveCustomerAdapter.cpfCnpj
        customer.personType = CpfCnpjUtils.getPersonType(saveCustomerAdapter.cpfCnpj)
        customer.address = address

        customer.save(flush: true, failOnError: true)
        return customer
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
