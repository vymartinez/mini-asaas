package mini.asaas.utils

class EmailUtils {

    public static Boolean isValid(String email) {
        if (email.empty) return false

        return email ==~ /[A-Za-z0-9_\%\+-]+(\.[A-Za-z0-9_\%\+-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*(\.[A-Za-z]{2,15})/
    }
}
