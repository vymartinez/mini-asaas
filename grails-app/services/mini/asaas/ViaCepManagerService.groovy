package mini.asaas

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import mini.asaas.adapters.ViaCepAdapter
import mini.asaas.dtos.ViaCepDTO
import mini.asaas.integration.viacep.ViaCepManager
import mini.asaas.utils.GsonBuilderUtils

@CompileStatic
@Transactional
class ViaCepManagerService {

    ViaCepAdapter get(String zipCode) {
        ViaCepManager viaCepManager = new ViaCepManager()
        viaCepManager.get("/${zipCode}/json/")

        if (!viaCepManager.isSuccessful()) throw new RuntimeException()
        if (!viaCepManager.responseBody) return null

        ViaCepDTO viaCepDTO = GsonBuilderUtils.buildClassFromJsonWithType((viaCepManager.responseBody as JSON).toString(), ViaCepDTO)
        return new ViaCepAdapter(viaCepDTO)
    }
}
