package mini.asaas.utils.user

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.user.User

@GrailsCompileStatic
trait SecurityUtils {

    SpringSecurityService sprigSecurityService

    User getCurrentUser() {
        sprigSecurityService.currentUser as User
    }

    boolean isAdmin() {
        currentUser?.authorities*.authority.contains('ROLE_ADMIN')
    }
}
