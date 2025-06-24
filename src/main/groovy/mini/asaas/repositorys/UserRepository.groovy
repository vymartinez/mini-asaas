package mini.asaas.repositorys

import mini.asaas.user.User
import grails.compiler.GrailsCompileStatic
import infrastructure.repository.Repository
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class UserRepository implements Repository<User, UserRepository> {

    @Override
    public void buildCriteria() {
        addCriteria {
            if (search.containsKey("username")) {
                eq("username", search.username)
            }
            if (search.containsKey("enabled")) {
                eq("enabled", search.enabled)
            }
            if (search.containsKey("accountExpired")) {
                eq("accountExpired", search.accountExpired)
            }
            if (search.containsKey("accountLocked")) {
                eq("accountLocked", search.accountLocked)
            }
            if (search.containsKey("passwordExpired")) {
                eq("passwordExpired", search.passwordExpired)
            }
        }
    }

    public BuildableCriteria getBuildableCriteria() {
        return User.createCriteria()
    }

    @Override
    public List<String> listAllowedFilters() {
        return [
            "username",
            "enabled",
            "accountExpired",
            "accountLocked",
            "passwordExpired"
        ]
    }
}