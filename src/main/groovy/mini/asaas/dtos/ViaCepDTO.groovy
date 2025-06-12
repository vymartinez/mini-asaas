package mini.asaas.dtos

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ViaCepDTO {

    String cep

    String logradouro

    String complemento

    String bairro

    String uf

    String ibge

    String localidade

    public ViaCepDTO(Map responseBody) {
        this.cep = responseBody.cep
        this.logradouro = responseBody.logradouro
        this.complemento = responseBody.complemento
        this.bairro = responseBody.bairro
        this.uf = responseBody.uf
        this.ibge = responseBody.ibge
        this.localidade = responseBody.localidade
    }
}
