package mini.asaas

import mini.asaas.adapters.ZipCodeInfoAdapter
import grails.compiler.GrailsCompileStatic
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class ZipCodeController {

    ZipCodeService zipCodeService

    @Secured("permitAll")
    def find() {
        try {
            ZipCodeInfoAdapter zipCodeInfo = zipCodeService.find(params.zipCode as String)
            render zipCodeInfo as JSON
        } catch (Exception e) {
            flash.message = "CEP não encontrado. Tente novamente."
            flash.success = false
        }
    }
}
