package mini.asaas.utils

import mini.asaas.adapters.AddressAdapter

class Utils {

    static Boolean emailIsValid(String email) {
        if (email.empty) return false

        return email ==~ /[A-Za-z0-9_\%\+-]+(\.[A-Za-z0-9_\%\+-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,15})/
    }

    static Object validateAddress(AddressAdapter addressAdapter, Object entity) {

        if (!addressAdapter.address) {
            DomainUtils.addError(entity, "O logradouro é obrigatório")
        }

        if (!addressAdapter.addressNumber) {
            DomainUtils.addError(entity, "O número do endereço é obrigatório")
        }

        if (!addressAdapter.province) {
            DomainUtils.addError(entity, "O bairro é obrigatório")
        }

        if (!addressAdapter.zipCode) {
            DomainUtils.addError(entity, "O CEP é obrigatório")
        }

        if (!addressAdapter.cityId) {
            DomainUtils.addError(entity, "Os dados de cidade são obrigatórios")
        }

        return entity
    }

    static String removeNonNumeric(String text) {
        if (text == null) return null

        return text?.replaceAll("\\D+","")
    }
}
