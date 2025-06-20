package mini.asaas.repositorys

import mini.asaas.Customer
import mini.asaas.enums.PersonType
import mini.asaas.utils.StringUtils

import grails.compiler.GrailsCompileStatic
import infrastructure.repository.Repository
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class CustomerRepository implements Repository<Customer, CustomerRepository> {

    @Override
    public void buildCriteria() {
        addCriteria {
            if (search.containsKey("name")) {
                like("name", "%${search.name}%")
            }

            if (search.containsKey("email")) {
                eq("email", search.email)
            }

            if (search.containsKey("cpfCnpj")) {
                eq("cpfCnpj", StringUtils.removeNonNumeric(search.cpfCnpj.toString()))
            }

            if (search.containsKey("personType")) {
                eq("personType", PersonType.convert(search.personType.toString()))
            }
        }
    }

    public BuildableCriteria getBuildableCriteria() {
        return Customer.createCriteria()
    }

    @Override
    public List<String> listAllowedFilters() {
        return [
            "name",
            "email",
            "cpfCnpj",
            "personType"
        ]
    }
}
