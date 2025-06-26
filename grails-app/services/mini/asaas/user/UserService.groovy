package mini.asaas.user

import mini.asaas.Customer
import mini.asaas.CustomerService
import mini.asaas.adapters.user.SaveUserAdapter
import mini.asaas.utils.DomainUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class UserService {

    CustomerService customerService
    SpringSecurityService springSecurityService

    public User create(SaveUserAdapter adapter) {
        User user = validate(adapter)

        if (user.hasErrors()) throw new ValidationException("Erro ao criar usuário", user.errors)

        Customer customer = customerService.create(adapter.customer)

        buildUser(adapter, user, customer)

        user.save(failOnError: true)
    }

    public User update(SaveUserAdapter adapter, User currentUser) {
        User user = validate(adapter)

        if (user.hasErrors()) throw new ValidationException("Erro ao atualizar usuário", user.errors)

        Customer customer = customerService.update(adapter.customer, currentUser.customer.id)

        buildUser(adapter, user, customer)

        user.save(failOnError: true)

        return user
    }

    private User validate(SaveUserAdapter adapter) {
        User user = new User()

        if (!adapter.username) DomainUtils.addError(user, "O nome de usuário é obrigatório")

        if (!adapter.password) DomainUtils.addError(user, "A senha é obrigatória")

        return user
    }

    private void buildUser(SaveUserAdapter adapter, User user, Customer customer) {
        user.username = adapter.username
        user.password = springSecurityService.encodePassword(adapter.password)
        user.customer = customer
        user.enabled = true
    }
}
