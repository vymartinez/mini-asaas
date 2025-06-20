package mini.asaas.adapters.user

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UpdateUserAdapter {

    String username

    String password

    Boolean enable

    List<String> roles = []

    public UpdateUserAdapter(Map params) {

        this.username = params.username as String

        this.password = params.password as String

        this.enable = params.enable != null ? params.enable.toString().toBoolean() : null

        def rawParam = params.roles

        if (rawParam instanceof List) {
            this.roles = rawParam*.toString()
        } else if (rawParam) {
            this.roles = rawParam.toString().split(',')*.trim()
        }
    }
}
