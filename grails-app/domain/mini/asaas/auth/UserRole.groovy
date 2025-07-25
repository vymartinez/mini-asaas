package mini.asaas.auth

import mini.asaas.role.Role
import mini.asaas.user.User

import grails.compiler.GrailsCompileStatic
import grails.gorm.DetachedCriteria
import groovy.transform.ToString
import org.codehaus.groovy.util.HashCodeHelper

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

    public static boolean remove(User user, Role role) {
        if (user != null && role != null) {
            return UserRole.where { user == user && role == role }.deleteAll()
        }
        return false
    }

    public static int removeAll(User user) {
        return user == null ? 0 : UserRole.where { user == user }.deleteAll() as int
    }

    public static int removeAll(Role role) {
        return role == null ? 0 : UserRole.where { role == role }.deleteAll() as int
    }

    static constraints = {
        user nullable: false
        role nullable: false, validator: { Role role, UserRole userRole ->
            if (userRole.user?.id) {
                if (UserRole.exists(userRole.user.id, role.id)) {
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
