package mini.asaas.utils.user

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.user.User

@GrailsCompileStatic
class SecurityUtils {

    SpringSecurityService sprigSecurityService

    public User getCurrentUser() {
        sprigSecurityService.currentUser as User
    }

    public boolean isAdmin() {
        currentUser?.authorities*.authority.contains('ROLE_ADMIN')
    }
}
