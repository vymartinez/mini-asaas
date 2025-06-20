package mini.asaas

import mini.asaas.adapters.ZipCodeInfoAdapter
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class ZipCodeService {

    CityService cityService
    ViaCepManagerService viaCepManagerService

    public ZipCodeInfoAdapter find(String zipCode) {
        ZipCodeInfoAdapter zipCodeInfoAdapter = viaCepManagerService.get(zipCode)

        zipCodeInfoAdapter.city = cityService.find(zipCodeInfoAdapter.name, zipCodeInfoAdapter.state, zipCodeInfoAdapter.ibgeCode)

        return zipCodeInfoAdapter
    }
}
