package mini.asaas

import mini.asaas.adapters.ZipCodeInfoAdapter
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ZipCodeController {

    ZipCodeService zipCodeService

    def find() {
        try {
            ZipCodeInfoAdapter zipCodeInfo = zipCodeService.find(params.zipCode as String)
            return [zipCodeInfo: zipCodeInfo]
        } catch (Exception e) {
            flash.message = "CEP não encontrado. Tente novamente."
            flash.success = false
        }
    }
}
