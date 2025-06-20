package mini.asaas.user

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.adapters.user.SaveUserAdapter
import mini.asaas.adapters.user.UpdateUserAdapter
import mini.asaas.auth.UserRole
import mini.asaas.role.Role

import mini.asaas.utils.user.SecurityUtils

@GrailsCompileStatic
@Transactional
class UserService {

    SpringSecurityService springSecurityService

    SecurityUtils securityUtils

    public User create(SaveUserAdapter adapter) {
        User currentUser = securityUtils.getCurrentUser()

        if (!currentUser || !securityUtils.isAdmin()) {
            throw new RuntimeException("Acesso negado: apenas administradores podem criar usuários.")
        }

        User user = new User()
        user.username = adapter.username
        user.password = adapter.password
        user.enabled = adapter.enable

        user.save(failOnError: true)

        adapter.roles.each { authority ->
            Role.findByAuthority(authority)?.with { role ->
                UserRole.create(user, role, true)
            }
        }

        return user
    }

    public User update(Long id, UpdateUserAdapter adapter) {
        User currentUser = securityUtils.getCurrentUser()

        User user = User.get(id)

        if (!user) {
            throw new RuntimeException("Usuário não encontrado para ID ${id}")
        }

        if (currentUser.id != id && !securityUtils.isAdmin()) {
            throw new RuntimeException("Acesso negado: apenas administradores podem atualizar outros usuários.")
        }

        user.username = adapter.username
        user.enabled =adapter.enable

        if (adapter.password) {
            user.password = springSecurityService.encodePassword(adapter.password)
        }

        user.save(failOnError: true)

        if (securityUtils.isAdmin()) {
            UserRole.removeAll(user)
            adapter.roles.each { authority ->
                Role.findByAuthority(authority)?.with { role ->
                    UserRole.create(user, role, true)
                }
            }
        }

        return user
    }

    public void softDelete(Long id) {
        User currentUser = securityUtils.getCurrentUser()

        if (!currentUser || !securityUtils.isAdmin()) {
            throw new RuntimeException("Acesso negado: apenas administradores podem desabilitar usuários.")
        }

        User user = User.get(id)

        if (!user) {
            throw new RuntimeException("Usuário não enconytrado para ID ${id}")
        }

        user.enabled = false
        user.save(failOnError: true)

    }

    public restore(Long id) {
        User currentUser = securityUtils.getCurrentUser()

        if (!currentUser || !securityUtils.isAdmin()) {
            throw new RuntimeException("Acesso negado: apenas administradores podem restaurar usuários.")
        }

        User user = User.get(id)

        if (!user) {
            throw new RuntimeException("Usuário não encontrado para o ID ${id}")
        }

        user.enabled = true
        user.save(failOnError: true)
    }

    public User get(Serializable id) {
        return User.get(id)
    }

    public List<User> list(Map params = [:]) {
        return User.list(params)
    }

    public count() {
        return User.count()
    }

    public boolean addRole(User user, String authority) {
        Role.findByAuthority(authority)?.with { role ->
            UserRole.create(user, role, true)
            return true
        } ?: false
    }

    public boolean removeRole(User user, String authority) {
        return Role.findByAuthority(authority)?.with { role ->
            UserRole.remove(user, role)
        } ?: false
    }
}
