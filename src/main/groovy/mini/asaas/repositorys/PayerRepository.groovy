package mini.asaas.repositorys

import mini.asaas.Payer
import mini.asaas.utils.StringUtils

import grails.compiler.GrailsCompileStatic
import infrastructure.repository.Repository
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class PayerRepository implements Repository<Payer, PayerRepository> {

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

            if (search.containsKey("cellPhone")) {
                eq("cellPhone", StringUtils.removeNonNumeric(search.cellPhone.toString()))
            }

            if (search.containsKey("nameOrEmail[like]")) {
                or {
                    like("name", "%${search.nameOrEmail}%")
                    like("email", "%${search.nameOrEmail}%")
                }
            }
        }
    }

    public BuildableCriteria getBuildableCriteria() {
        return Payer.createCriteria()
    }

    @Override
    public List<String> listAllowedFilters() {
        return [
            "name",
            "email",
            "cpfCnpj",
            "cellPhone",
            "customerId",
            "nameOrEmail[like]"
        ]
    }
}
