package mini.asaas.adapters

import mini.asaas.City
import mini.asaas.dtos.ViaCepDTO
import mini.asaas.enums.State
import groovy.transform.CompileStatic

@CompileStatic
class ViaCepAdapter {

    String zipCode

    String address

    String complement

    String province

    State state

    String ibgeCode

    String district

    List<City> cities

    public ViaCepAdapter(ViaCepDTO viaCepDto) {
        this.zipCode = viaCepDto.cep
        this.address = viaCepDto.logradouro
        this.complement = viaCepDto.complemento
        this.province = viaCepDto.bairro
        this.state = State.valueOf(viaCepDto.uf.toUpperCase())
        this.ibgeCode = viaCepDto.ibge
        this.district = viaCepDto.localidade
        this.cities = []
    }
}
