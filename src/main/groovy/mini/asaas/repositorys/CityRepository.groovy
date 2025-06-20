package mini.asaas.repositorys

import grails.compiler.GrailsCompileStatic
import infrastructure.repository.Repository
import mini.asaas.City
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class CityRepository implements Repository<City, CityRepository> {

    @Override
    public void buildCriteria() {
        addCriteria {
            if (search.containsKey("name")) {
                eq("name", search.name)
            }

            if (search.containsKey("state")) {
                eq("state", search.state)
            }

            if (search.containsKey("ibgeCode")) {
                eq("ibgeCode", search.ibgeCode)
            }
        }
    }

    public BuildableCriteria getBuildableCriteria() {
        return City.createCriteria()
    }

    @Override
    public List<String> listAllowedFilters() {
        return [
            "ibgeCode",
            "name",
            "state"
        ]
    }
}
