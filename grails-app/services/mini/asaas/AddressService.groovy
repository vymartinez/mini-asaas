package mini.asaas

import grails.validation.ValidationException
import mini.asaas.adapters.AddressAdapter
import mini.asaas.utils.DomainUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class AddressService {

    public create(AddressAdapter addressAdapter) {
        Address address = validate(addressAdapter)

        if (address.hasErrors()) throw new ValidationException("Erro ao criar endereço", address.errors)

        address.address = addressAdapter.address
        address.addressNumber = addressAdapter.addressNumber
        address.city = City.get(addressAdapter.cityId)
        address.zipCode = addressAdapter.zipCode
        address.province = addressAdapter.province
        address.complement = addressAdapter.complement

        address.save(failOnError: true)
        return address
    }

    public Address validate(AddressAdapter addressAdapter) {
        Address address = new Address()

        if (!addressAdapter.address) DomainUtils.addError(address, "O logradouro é obrigatório")

        if (!addressAdapter.addressNumber) DomainUtils.addError(address, "O número do endereço é obrigatório")

        if (!addressAdapter.province) DomainUtils.addError(address, "O bairro é obrigatório")

        if (!addressAdapter.zipCode) DomainUtils.addError(address, "O CEP é obrigatório")

        if (!addressAdapter.cityId) DomainUtils.addError(address, "Os dados de cidade são obrigatórios")

        return address
    }
}
