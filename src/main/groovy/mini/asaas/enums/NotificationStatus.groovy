package mini.asaas.enums

enum NotificationStatus {

    CREATED,
    PAID,
    EXPIRED,
    DELETED

    static NotificationStatus convert(String notificationStatus) {
        try {
            if (notificationStatus instanceof String) notificationStatus = notificationStatus.toUpperCase()
            return notificationStatus as NotificationStatus
        } catch(Exception e) {
            return null
        }
    }
}