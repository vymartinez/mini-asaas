package mini.asaas.userdetails

import mini.asaas.repositorys.UserRepository
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.userdetails.GrailsUser

@GrailsCompileStatic
class CustomUserDetails extends GrailsUser {

    Long customerId

    CustomUserDetails(GrailsUser grailsUser) {
        super(
            grailsUser.username,
            grailsUser.password,
            grailsUser.enabled,
            grailsUser.accountNonExpired,
            grailsUser.credentialsNonExpired,
            grailsUser.accountNonLocked,
            grailsUser.authorities,
            grailsUser.id)
        if (!this.id) return

        this.customerId = UserRepository.query([column: "customer.id", id: this.id]).get()
    }
}