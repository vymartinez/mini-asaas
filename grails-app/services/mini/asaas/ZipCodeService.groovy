package mini.asaas

import mini.asaas.adapters.ZipCodeInfoAdapter
import mini.asaas.utils.StringUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class ZipCodeService {

    CityService cityService
    ViaCepManagerService viaCepManagerService

    public ZipCodeInfoAdapter find(String zipCode) {
        ZipCodeInfoAdapter zipCodeInfoAdapter = viaCepManagerService.get(StringUtils.removeNonNumeric(zipCode))

        zipCodeInfoAdapter.city = cityService.find(zipCodeInfoAdapter.name, zipCodeInfoAdapter.state, zipCodeInfoAdapter.ibgeCode)

        return zipCodeInfoAdapter
    }
}
