package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class OnboardingController {

    @Secured("permitAll")
    def createCustomer() {}
}
