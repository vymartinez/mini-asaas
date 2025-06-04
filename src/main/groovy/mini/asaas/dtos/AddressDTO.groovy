package mini.asaas.dtos

class AddressDTO {
    String address
    Integer addressNumber
    String city
    String zipCode
    String province
    String complement

    AddressDTO(Map params) {
        this.address = params.address
        this.addressNumber = Integer.parseInt(params.addressNumber.toString())
        this.city = params.city
        this.zipCode = params.zipCode
        this.province = params.province
        this.complement = params.complement
    }
}