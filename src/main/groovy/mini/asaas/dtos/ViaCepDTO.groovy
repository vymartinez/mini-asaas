package mini.asaas.dtos

import groovy.transform.CompileStatic

@CompileStatic
class ViaCepDTO {

    String cep

    String logradouro

    String complemento

    String bairro

    String uf

    String ibge

    String localidade

    public ViaCepDTO(Map body) {
        this.cep = body.'cep'
        this.logradouro = body.'logradouro'
        this.complemento = body.'complemento'
        this.bairro = body.'bairro'
        this.uf = body.'uf'
        this.ibge = body.'ibge'
        this.localidade = body.'localidade'
    }
}
