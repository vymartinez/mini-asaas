package mini.asaas

import grails.gorm.transactions.Transactional
import mini.asaas.dtos.UserDTO

@Transactional
class UserService {

    def save(UserDTO userDto) {
        User user = new User(
            name: userDto.name,
            password: userDto.password
        )
        user.save(failOnError: true)
        return user
    }
}
