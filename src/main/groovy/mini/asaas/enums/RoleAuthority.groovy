package mini.asaas.enums

enum RoleAuthority {

    ROLE_USER,
    ROLE_ADMIN

    static RoleAuthority convert(String authority) {
        try {
            if (authority instanceof String) authority = authority.toUpperCase()
            return authority as RoleAuthority
        } catch(Exception e) {
            return null
        }
    }
}
