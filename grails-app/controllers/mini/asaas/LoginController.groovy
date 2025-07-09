package mini.asaas

import mini.asaas.enums.MessageType
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured
import grails.plugin.springsecurity.SpringSecurityService

@Secured(['permitAll'])
class LoginController extends BaseController {

    SpringSecurityService springSecurityService

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect(action: 'auth', params: params)
        }
    }

    def auth() {
        ConfigObject conf = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            redirect(uri: conf.successHandler.defaultTargetUrl)
            return
        }

        String postUrl = request.contextPath + conf.apf.filterProcessesUrl
        return [postUrl: postUrl]
    }

    def authfail() {
        buildFlashAlert("Usuário ou senha inválidos.", MessageType.ERROR, false)
        redirect(action: "auth", params: params)
    }
}