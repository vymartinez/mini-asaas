package mini.asaas.repositorys

import mini.asaas.Address

import grails.compiler.GrailsCompileStatic
import infrastructure.repository.Repository
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class AddressRepository implements Repository<Address, AddressRepository> {

    @Override
    public void buildCriteria() {
        addCriteria {
            if (search.containsKey("address")) {
                eq("address", search.address)
            }

            if (search.containsKey("addressNumber")) {
                eq("addressNumber", search.addressNumber)
            }

            if (search.containsKey("province")) {
                eq("province", search.province)
            }

            if (search.containsKey("zipCode")) {
                eq("zipCode", search.zipCode)
            }

            if (search.containsKey("complement")) {
                eq("complement", search.complement)
            }
        }
    }

    public BuildableCriteria getBuildableCriteria() {
        return Address.createCriteria()
    }

    @Override
    public List<String> listAllowedFilters() {
        return [
            "address",
            "addressNumber",
            "province",
            "zipCode",
            "complement"
        ]
    }
}
