package mini.asaas.adapters

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UpdateUserAdapter {

    Boolean updateUsername = false

    String username

    Boolean updatePassword = false

    String password

    Boolean updateEnable = false

    Boolean enable

    Boolean updateRoles = false

    List<String> roles = []

    public UpdateUserAdapter(Map params) {
        this.updateUsername = params.updateUsername?.toString().toBoolean() ?: false
        this.username = params.username as String

        this.updatePassword = params.updatePassword?.toString().toBoolean() ?: false
        this.password = params.password as String

        this.updateEnable = params.updateEnable?.toString().toBoolean() ?: false
        this.enable = params.enable != null ? params.enable.toString().toBoolean() : null

        this.updateRoles = params.updateRoles?.toString().toBoolean() ?: false

        def rolesParam = params.roles

        if (rolesParam instanceof List) {
            this.roles = rolesParam*.toString()
        } else if (rolesParam) {
            this.roles = rolesParam.toString().split(',')*.trim()
        }
    }
}
