package mini.asaas

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import mini.asaas.adapters.ViaCepAdapter

@Transactional
@CompileStatic
class CityService {

    ViaCepManagerService viaCepManagerService

    ViaCepAdapter findAllByZipCode(String zipCode) {

        ViaCepAdapter viaCepAdapter = viaCepManagerService.get(zipCode)

        viaCepAdapter.cities = findAllByIbgeCode(viaCepAdapter.ibgeCode)

        return viaCepAdapter
    }

    private List<City> findAllByIbgeCode(String ibgeCode) {
        return City.executeQuery("from City where ibgeCode = :ibgeCode", [ibgeCode: ibgeCode])
    }
}
