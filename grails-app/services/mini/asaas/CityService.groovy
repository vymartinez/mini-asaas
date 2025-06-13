package mini.asaas

import mini.asaas.enums.State
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import mini.asaas.repositorys.CityRepository

@Transactional
@GrailsCompileStatic
class CityService {

    public City find(String name, State state, String ibgeCode) {
        return CityRepository.query([name: name, state: state, ibgeCode: ibgeCode]).get()
    }
}
