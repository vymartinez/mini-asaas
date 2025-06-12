package mini.asaas

import mini.asaas.adapters.ZipCodeInfoAdapter
import mini.asaas.dtos.ViaCepDTO
import mini.asaas.integration.viacep.ViaCepManager
import mini.asaas.utils.GsonBuilderUtils
import grails.converters.JSON
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class ViaCepManagerService {

    ZipCodeInfoAdapter get(String zipCode) {
        ViaCepManager viaCepManager = new ViaCepManager()
        viaCepManager.get("/${zipCode}/json")

        if (!viaCepManager.isSuccessful()) throw new RuntimeException()
        if (!viaCepManager.responseBody) return null

        ViaCepDTO viaCepDTO = GsonBuilderUtils.buildClassFromJsonWithType((viaCepManager.responseBody as JSON).toString(), ViaCepDTO)
        return new ZipCodeInfoAdapter(viaCepDTO)
    }
}
