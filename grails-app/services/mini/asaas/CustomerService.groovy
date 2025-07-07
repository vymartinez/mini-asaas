package mini.asaas

import mini.asaas.adapters.SaveCustomerAdapter
import mini.asaas.repositorys.CustomerRepository
import mini.asaas.utils.CpfCnpjUtils
import mini.asaas.utils.DomainUtils
import mini.asaas.utils.EmailUtils
import mini.asaas.utils.StringUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class CustomerService {

    AddressService addressService

    public Customer create(SaveCustomerAdapter saveCustomerAdapter) {
        Customer customer = validate(saveCustomerAdapter)

        if (customer.hasErrors()) throw new ValidationException("Erro ao criar conta do usuário", customer.errors)

        Address address = addressService.create(saveCustomerAdapter.address)
        buildCustomer(customer, saveCustomerAdapter, address)

        customer.save(failOnError: true)
        return customer
    }

    public Customer update(SaveCustomerAdapter saveCustomerAdapter, Long customerId) {
        Customer customer = validate(saveCustomerAdapter)

        if (customer.hasErrors()) throw new ValidationException("Erro ao atualizar a conta do usuário", customer.errors)

        customer = findById(customerId)

        Address address = addressService.update(saveCustomerAdapter.address, customer.address.id)
        buildCustomer(customer, saveCustomerAdapter, address)

        customer.save(failOnError: true)
        return customer
    }

    public Customer findById(Long customerId) {
        Customer customer = CustomerRepository.query([id: customerId]).get()

        if (!customer) throw new RuntimeException("Usuário não encontrado")

        return customer
    }

    private Customer validate(SaveCustomerAdapter saveCustomerAdapter) {
        Customer customer = new Customer()

        if (!saveCustomerAdapter.name) DomainUtils.addError(customer, "O nome é obrigatório")

        if (!saveCustomerAdapter.email) DomainUtils.addError(customer, "O email é obrigatório")

        if (saveCustomerAdapter.email && !EmailUtils.isValid(saveCustomerAdapter.email)) DomainUtils.addError(customer, "O email informado é inválido")

        if (!saveCustomerAdapter.cpfCnpj) DomainUtils.addError(customer, "O CPF/CNPJ é obrigatório")

        if (saveCustomerAdapter.cpfCnpj && !CpfCnpjUtils.validate(saveCustomerAdapter.cpfCnpj)) DomainUtils.addError(customer, "O CPF/CNPJ informado é inválido")

        return customer
    }

    private void buildCustomer(Customer customer, SaveCustomerAdapter saveCustomerAdapter, Address address) {
        customer.name = saveCustomerAdapter.name
        customer.email = saveCustomerAdapter.email
        customer.cpfCnpj = StringUtils.removeNonNumeric(saveCustomerAdapter.cpfCnpj)
        customer.personType = CpfCnpjUtils.getPersonType(saveCustomerAdapter.cpfCnpj)
        customer.address = address
    }
}
