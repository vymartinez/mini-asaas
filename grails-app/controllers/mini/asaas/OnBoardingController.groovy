package mini.asaas

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class OnBoardingController {

    @Secured("permitAll")
    def createCustomer() {}
}
