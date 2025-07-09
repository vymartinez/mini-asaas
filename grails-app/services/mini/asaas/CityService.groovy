package mini.asaas

import mini.asaas.enums.State
import mini.asaas.repositorys.CityRepository

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class CityService {

    public City find(String name, State state, String ibgeCode) {
        return CityRepository.query([name: name, state: state, ibgeCode: ibgeCode]).get()
    }
}
