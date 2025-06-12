package mini.asaas

import mini.asaas.adapters.ZipCodeInfoAdapter
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class ZipCodeService {

    ViaCepManagerService viaCepManagerService
    CityService cityService

    public ZipCodeInfoAdapter find(String zipCode) {
        ZipCodeInfoAdapter zipCodeInfoAdapter = viaCepManagerService.get(zipCode)

        zipCodeInfoAdapter.city = cityService.search(zipCodeInfoAdapter.name, zipCodeInfoAdapter.state, zipCodeInfoAdapter.ibgeCode)

        return zipCodeInfoAdapter
    }
}
