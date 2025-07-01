package mini.asaas.enums

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
enum NotificationType {

    PAYMENT_CREATED,
    PAYMENT_PAID,
    PAYMENT_EXPIRED,
    PAYMENT_DELETED

    static NotificationType convert(String notificationType) {
        try {
            if (notificationType instanceof String) notificationType = notificationType.toUpperCase()
            return notificationType as NotificationType
        } catch(Exception e) {
            return null
        }
    }
}