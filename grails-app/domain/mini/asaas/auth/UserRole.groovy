package mini.asaas.auth

import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import mini.asaas.role.Role
import mini.asaas.user.User
import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class UserRole implements Serializable {

    private static final long serialVersionUID = 1

    User user

    Role role

    @Override
    public boolean equals(other) {
        if (other instanceof UserRole) {
            return other.userId == user?.id && other.roleId == role?.id
        }
        return false
    }

    @Override
    public int hashCode() {

        int hashCode = HashCodeHelper.initHash()

        if (user) {
            hashCode = HashCodeHelper.updateHash(hashCode, user.id)
        }

        if (role) {
            hashCode = HashCodeHelper.updateHash(hashCode, role.id)
        }
        return hashCode
    }

    public static UserRole get(long userId, long roleId) {
        return criteriaFor(userId, roleId).get()
    }

    public static boolean exists(long userId, long roleId) {
        return criteriaFor(userId, roleId).count()
    }

    private static DetachedCriteria criteriaFor(long userId, long roleId) {
        UserRole.where {
            user == User.load(userId) &&
            role == Role.load(roleId)
        }
    }

    public static UserRole create(User user, Role role, boolean flush = false) {
        def instance = new UserRole(user: user, role: role)
        instance.save(flush: flush)
        return instance
    }

    public static boolean remove(User u, Role r) {
        if (u != null && r != null) {
            return UserRole.where { user == u && role == r }.deleteAll()
        }
        return false
    }

    public static int removeAll(User u) {
        return u == null ? 0 : UserRole.where { user == u }.deleteAll() as int
    }

    public static int removeAll(Role r) {
        return r == null ? 0 : UserRole.where { role == r }.deleteAll() as int
    }

    static constraints = {
        user nullable: false
        role nullable: false, validator: { Role r, UserRole ur ->
            if (ur.user?.id) {
                if (UserRole.exists(ur.user.id, r.id)) {
                    return ['userRole.exists']
                }
            }
        }
    }

    static mapping = {
        id composite: ['user', 'role']
        version false
    }
}
