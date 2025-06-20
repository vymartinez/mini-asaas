package mini.asaas

import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService

class LoginController {

    SpringSecurityService springSecurityService

    @Secured(['permitAll'])
    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect(uri: '/')
            return
        }
        render(view: 'login')
    }

    @Secured(['permitAll'])
    def authenticate() {
        def username = params.username
        def password = params.password

        def user = mini.asaas.user.User.findByUsername(username)
        if (user && springSecurityService.passwordEncoder.isPasswordValid(user.password, password, null)) {
            springSecurityService.reauthenticate(username)
            redirect(uri: '/')
        } else {
            flash.message = "Usuário ou senha inválidos."
            redirect(action: 'index')
        }
    }

    def logout() {
        springSecurityService.logout()
        redirect(action: 'index')
    }
}