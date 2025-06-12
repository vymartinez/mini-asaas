package mini.asaas.adapters

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class AddressAdapter {

    String address

    Integer addressNumber

    Long cityId

    String zipCode

    String province

    String complement

    public AddressAdapter(Map params) {
        this.address = params.address
        this.addressNumber = params.addressNumber as Integer
        this.cityId = params.cityId as Long
        this.zipCode = params.zipCode
        this.province = params.province
        this.complement = params.complement
    }
}
