package mini.asaas.user

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import mini.asaas.adapters.SaveUserAdapter
import mini.asaas.adapters.UpdateUserAdapter
import mini.asaas.auth.UserRole
import mini.asaas.role.Role

@Transactional
class UserService {

    SpringSecurityService springSecurityService

    public User create(SaveUserAdapter adapter) {

        def currentUser = springSecurityService.currentUser as User

        if (!currentUser.authorities*.authority.contains('ROLE_ADMIN')) {
            throw new RuntimeException("Acesso negado: apenas administradores podem criar usuários.")
        }

        User user = new User(
                username: adapter.username,
                password: adapter.password,
                enabled: adapter.enable
        )

        user.save(failOnError: true)

        adapter.roles.each { authority ->
            Role role = Role.findByAuthority(authority)
            if (role) {
                UserRole.create(user, role, true)
            }
        }

        return user
    }

    public User update(Long id, UpdateUserAdapter adapter) {

        def currentUser = springSecurityService.currentUser as User

        User user = User.get(id)

        if (!user) {
            throw new RuntimeException("Usuário não encontrado para ID ${id}")
        }

        if (currentUser.id != id && !currentUser.authorities*.authority.contains('ROLE_ADMIN')) {
            throw new RuntimeException("Acesso negado: apenas administradores podem atualizar outros usuários.")
        }

        if (adapter.updateUsername) {
            user.username = adapter.username
        }

        if (adapter.updatePassword) {
            user.password = springSecurityService.encodePassword(adapter.password)
        }

        if (adapter.updateEnable && currentUser.authorities*.authority.contains('ROLE_ADMIN')) {
            user.enabled = adapter.enable
        }

        user.save(failOnError: true)

        if (adapter.updateRoles && currentUser.authorities*.authority.contains('ROLE_ADMIN')) {
            UserRole.removeAll(user)
            adapter.roles.each { authority ->
                Role role = Role.findByAuthority(authority)
                if (role) {
                    UserRole.create(user, role, true)
                }
            }
        }

        return user
    }

    public void softDelete(Long id) {

        def currentUser = springSecurityService.currentUser as User

        User user = User.get(id)

        if (!user) {
            throw new RuntimeException("Usuário não encontrado para ID ${id}")
        }

        if (!currentUser.authorities*.authority.contains('ROLE_ADMIN')) {
            throw new RuntimeException("Acesso negado: apenas administradores podem desabilitar usuários.")
        }

        user.enabled = false
        user.save(failOnError: true)
    }

    public restore(Long id) {

        def currentUser = springSecurityService.currentUser as User

        User user = User.get(id)

        if (!user) {
            throw new RuntimeException("Usuário não encontrado para ID ${id}")
        }

        if (!currentUser.authorities*.authority.contains('ROLE_ADMIN')) {
            throw new RuntimeException("Acesso negado: apenas administradores podem restaurar usuários.")
        }

        user.enabled = true
        user.save(failOnError: true)
    }

    public User get(Serializable id) {
        User.get(id)
    }

    public List<User> list(Map args = [:]) {
        args.max = Math.min((args.max as Integer) ?: 10, 100)
        args.offset = (args.offset as Integer) ?: 0
        User.list(args)
    }

    public count() {
        User.count()
    }

    public boolean addRole(User user, String authority) {
        Role.findByAuthority(authority)?.with { role ->
            UserRole.create(user, role, true)
            true
        } ?: false
    }

    public boolean removeRole(User user, String authority) {
        Role.findByAuthority(authority)?.with { role ->
            UserRole.remove(user, role)
        } ?: false
    }
}
