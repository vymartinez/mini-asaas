package mini.asaas

import mini.asaas.adapters.ViaCepAdapter
import groovy.transform.CompileStatic

@CompileStatic
class CityController {

    CityService cityService

    def find() {
        try {
            ViaCepAdapter cityInfo = cityService.findAllByZipCode(params.zipCode as String)
            return [cityInfo: cityInfo]
        } catch (Exception e) {
            flash.message = message(success: false, error: "CEP não encontrado. Tente novamente.")
        }
    }
}
