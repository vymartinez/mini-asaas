package mini.asaas.adapters

import mini.asaas.City
import mini.asaas.dtos.ViaCepDTO
import mini.asaas.enums.State
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ZipCodeInfoAdapter {

    String zipCode

    String address

    String complement

    String province

    State state

    String ibgeCode

    String name

    City city

    public ZipCodeInfoAdapter(ViaCepDTO viaCepDto) {
        this.zipCode = viaCepDto.cep
        this.address = viaCepDto.logradouro
        this.complement = viaCepDto.complemento
        this.province = viaCepDto.bairro
        this.state = State.valueOf(viaCepDto.uf.toUpperCase())
        this.ibgeCode = viaCepDto.ibge
        this.name = viaCepDto.localidade
    }
}
