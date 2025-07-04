package mini.asaas

import grails.gorm.PagedResultList
import mini.asaas.adapters.SavePayerAdapter
import mini.asaas.repositorys.PayerRepository
import mini.asaas.utils.CpfCnpjUtils
import mini.asaas.utils.DomainUtils
import mini.asaas.utils.EmailUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class PayerService {

    AddressService addressService
    CustomerService customerService

    public Payer create(SavePayerAdapter savePayerAdapter, Long customerId) {
        Payer payer = validate(savePayerAdapter)

        if (payer.hasErrors()) throw new ValidationException("Erro ao criar pagador", payer.errors)

        Customer customer = customerService.findById(customerId)
        Address address = addressService.create(savePayerAdapter.address)

        buildPayer(payer, savePayerAdapter, customer, address)

        payer.save(failOnError: true)
        return payer
    }

    public PagedResultList<Payer> list(Map params, Long customerId, Integer max, Integer offset) {
        Map filters = buildListFilters(params, customerId)

        return PayerRepository.query(filters).readOnly().list([max: max, offset: offset])
    }

    public Payer update(SavePayerAdapter savePayerAdapter, Long payerId, Long customerId) {
        Payer payer = validate(savePayerAdapter)

        if (payer.hasErrors()) throw new ValidationException("Erro ao atualizar pagador", payer.errors)

        payer = findById(payerId, customerId)

        Customer customer = customerService.findById(customerId)

        Address address = addressService.update(savePayerAdapter.address, payer.address.id)

        buildPayer(payer, savePayerAdapter, customer, address)

        payer.save(failOnError: true)
        return payer
    }

    public Payer findById(Long payerId, Long customerId) {
        Payer payer = PayerRepository.query([id: payerId, includeDeleted: true]).get()

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        if (customerId != payer.customer.id) throw new RuntimeException("O pagador não pertence ao cliente logado")

        return payer
    }

    public void disable(Long payerId, Long customerId) {
        Payer payer = findById(payerId, customerId)

        addressService.disable(payer.address.id)

        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void restore(Long payerId, Long customerId) {
        Payer payer = findById(payerId, customerId)

        addressService.restore(payer.address.id)

        payer.deleted = false
        payer.save(failOnError: true)
    }

    private Payer validate(SavePayerAdapter savePayerAdapter) {
        Payer payer = new Payer()

        if (!savePayerAdapter.name) DomainUtils.addError(payer, "O nome é obrigatório")

        if (!savePayerAdapter.email) DomainUtils.addError(payer, "O email é obrigatório")

        if (savePayerAdapter.email && !EmailUtils.isValid(savePayerAdapter.email)) DomainUtils.addError(payer, "O email informado é inválido")

        if (!savePayerAdapter.cpfCnpj) DomainUtils.addError(payer, "O CPF/CNPJ é obrigatório")

        if (savePayerAdapter.cpfCnpj && !CpfCnpjUtils.validate(savePayerAdapter.cpfCnpj)) DomainUtils.addError(payer, "O CPF/CNPJ informado é inválido")

        if (!savePayerAdapter.cellPhone) DomainUtils.addError(payer, "O celular é obrigatório")

        return payer
    }

    private void buildPayer(Payer payer, SavePayerAdapter savePayerAdapter, Customer customer, Address address) {
        payer.name = savePayerAdapter.name
        payer.email = savePayerAdapter.email
        payer.cpfCnpj = savePayerAdapter.cpfCnpj
        payer.cellPhone = savePayerAdapter.cellPhone
        payer.address = address
        payer.customer = customer
    }

    private Map buildListFilters(Map params, customerId) {
        Map filters = [:]

        if (params."nameOrEmail[like]") filters."nameOrEmail[like]" = params."nameOrEmail[like]"
        filters.customerId = customerId
        filters.includeDeleted = true

        return filters
    }
}
