package mini.asaas.dtos

class UserDTO {
    String name
    String password

    UserDTO(Map params) {
        this.name = params.name
        this.password = params.password
    }
}
