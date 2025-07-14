package mini.asaas.user

import mini.asaas.Customer
import mini.asaas.CustomerService
import mini.asaas.adapters.user.SaveUserAdapter
import mini.asaas.auth.UserRole
import mini.asaas.enums.RoleAuthority
import mini.asaas.role.Role
import mini.asaas.utils.DomainUtils

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@GrailsCompileStatic
@Transactional
class UserService {

    CustomerService customerService

    public void create(SaveUserAdapter adapter) {
        User user = validate(adapter)

        if (user.hasErrors()) throw new ValidationException("Erro ao criar usuário", user.errors)

        Customer customer = customerService.create(adapter.customer)

        buildUser(adapter, user, customer)

        user.save(failOnError: true)

        createUserRole(user)
    }

    public User update(SaveUserAdapter adapter, User currentUser) {
        User user = validate(adapter)

        if (user.hasErrors()) throw new ValidationException("Erro ao atualizar usuário", user.errors)

        user = currentUser

        Customer customer = customerService.update(adapter.customer, currentUser.customer.id)

        buildUser(adapter, user, customer)

        user.save(failOnError: true)

        return user
    }

    private void createUserRole(User user) {
        UserRole defaultUserRole = new UserRole(user: user, role: Role.findByAuthority(RoleAuthority.ROLE_USER.toString()))

        defaultUserRole.save(failOnError: true)
    }

    private User validate(SaveUserAdapter adapter) {
        User user = new User()

        if (!adapter.username) DomainUtils.addError(user, "O nome de usuário é obrigatório")

        if (!adapter.password) DomainUtils.addError(user, "A senha é obrigatória")

        return user
    }

    private void buildUser(SaveUserAdapter adapter, User user, Customer customer) {
        user.username = adapter.username
        user.password = adapter.password
        user.customer = customer
        user.enabled = true
    }
}
