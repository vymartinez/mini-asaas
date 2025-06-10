package mini.asaas.user

import mini.asaas.auth.UserRole
import mini.asaas.role.Role

import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    String username

    String password

    boolean enabled = true

    boolean accountExpired

    boolean accountLocked

    boolean passwordExpired

    public Set<Role> getAuthorities() {
        return (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
    }

    static mapping = {
        password column: 'password'
    }
}
