package mini.asaas

import mini.asaas.adapters.AddressAdapter
import mini.asaas.repositorys.AddressRepository
import mini.asaas.utils.DomainUtils
import mini.asaas.utils.StringUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class AddressService {

    public Address create(AddressAdapter addressAdapter) {
        Address address = validate(addressAdapter)

        if (address.hasErrors()) throw new ValidationException("Erro ao criar endereço", address.errors)

        buildAddress(address, addressAdapter)

        address.save(failOnError: true)
        return address
    }

    public Address update(AddressAdapter addressAdapter, Long addressId) {
        Address address = validate(addressAdapter)

        if (address.hasErrors()) throw new ValidationException("Erro ao atualizar endereço", address.errors)

        address = findById(addressId)

        buildAddress(address, addressAdapter)

        address.save(failOnError: true)
        return address
    }

    public void disable(Long addressId) {
        Address address = findById(addressId)

        address.deleted = true
        address.save(failOnError: true)
    }

    public void restore(Long addressId) {
        Address address = findById(addressId)

        address.deleted = false
        address.save(failOnError: true)
    }

    private Address findById(Long addressId) {
        Address address = AddressRepository.query([id: addressId, includeDeleted: true]).get()

        if (!address) throw new RuntimeException("Endereço não encontrado")

        return address
    }

    private Address validate(AddressAdapter addressAdapter) {
        Address address = new Address()

        if (!addressAdapter.address) DomainUtils.addError(address, "O logradouro é obrigatório")

        if (!addressAdapter.addressNumber) DomainUtils.addError(address, "O número do endereço é obrigatório")

        if (!addressAdapter.province) DomainUtils.addError(address, "O bairro é obrigatório")

        if (!addressAdapter.zipCode) DomainUtils.addError(address, "O CEP é obrigatório")

        if (!addressAdapter.cityId) DomainUtils.addError(address, "Os dados de cidade são obrigatórios")

        return address
    }

    private void buildAddress(Address address, AddressAdapter addressAdapter) {
        address.address = addressAdapter.address
        address.addressNumber = addressAdapter.addressNumber
        address.city = City.get(addressAdapter.cityId)
        address.zipCode = StringUtils.removeNonNumeric(addressAdapter.zipCode)
        address.province = addressAdapter.province
        address.complement = addressAdapter.complement
    }
}
