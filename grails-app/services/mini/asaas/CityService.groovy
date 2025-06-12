package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import mini.asaas.enums.State

@Transactional
@GrailsCompileStatic
class CityService {

    public City search(String name, State state, String ibgeCode) {
        City city = new City()
        city.name = name
        city.ibgeCode = ibgeCode
        city.state = state

        return City.find(city)
    }
}
