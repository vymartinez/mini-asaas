package mini.asaas.utils.user

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.SpringSecurityService
import grails.util.Holders
import mini.asaas.Customer
import mini.asaas.user.User
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder

@GrailsCompileStatic
class UserUtils {

    public static User getCurrentUser(Boolean throwExceptionIfNull = false) {

        SpringSecurityService springSecurityService = getSpringSecurityService()

        if (springSecurityService?.currentUserId) {
            return User.get(springSecurityService.currentUserId as Long)
        }

        if (springSecurityService?.currentUser) {
            return springSecurityService.currentUser as User
        }

        GrailsWebRequest webRequest = (GrailsWebRequest) RequestContextHolder.getRequestAttributes()

        User currentUser = springSecurityService.currentUser as User

        if (currentUser instanceof User) {
            return currentUser
        }

        if (throwExceptionIfNull) {
            throw new RuntimeException("Usuário não encontrado na sessão.")
        }

        return null
    }

    public static Long getCurrentUserId(Boolean throwExceptionIfNull = false) {
        User user = getCurrentUser(throwExceptionIfNull)
        return user?.id
    }

    public static Customer getCurrentCustomer(Boolean throwExceptionIfNull = false) {

        User user = getCurrentUser(throwExceptionIfNull)

        if (!user) {
            return null
        }

        Customer customer = Customer.findWhere(user: user)

        if (!customer && throwExceptionIfNull) {
            throw new RuntimeException("Customer não encontrado para o usuário: ${user.username}")
        }

        return customer
    }

    public static Long getCurrentCustomerId(Boolean throwExceptionIfNull = false) {
        Customer customer = getCurrentCustomer(throwExceptionIfNull)
        return customer?.id
    }

    private static SpringSecurityService getSpringSecurityService() {
        return Holders.applicationContext.getBean("springSecurityService") as SpringSecurityService
    }
}
