package mini.asaas

import grails.gorm.transactions.Transactional
import mini.asaas.adapters.AddressAdapter
import mini.asaas.utils.DomainUtils

@Transactional
class AddressService {

    public Object validate(AddressAdapter addressAdapter, Object entity) {

        if (!addressAdapter.address) DomainUtils.addError(entity, "O logradouro é obrigatório")

        if (!addressAdapter.addressNumber) DomainUtils.addError(entity, "O número do endereço é obrigatório")

        if (!addressAdapter.province) DomainUtils.addError(entity, "O bairro é obrigatório")

        if (!addressAdapter.zipCode) DomainUtils.addError(entity, "O CEP é obrigatório")

        if (!addressAdapter.cityId) DomainUtils.addError(entity, "Os dados de cidade são obrigatórios")

        return entity
    }
}
