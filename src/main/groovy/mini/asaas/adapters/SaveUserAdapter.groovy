package mini.asaas.adapters

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class SaveUserAdapter {

    String username

    String password

    Boolean enable = true

    List<String> roles = []

    public SaveUserAdapter(Map params) {
        this.username = params.username as String
        this.password = params.password as String
        this.enable = params.enable != null ? params.enable.toString().toBoolean() : true

        def rolesParam = params.roles

        if (rolesParam instanceof List) {
            this.roles = rolesParam*.toString()
        } else if (rolesParam) {
            this.roles = rolesParam.toString().split(',')*.trim()
        }
    }
}
