package mini.asaas

import mini.asaas.enums.NotificationStatus
import mini.asaas.payment.Payment
import mini.asaas.utils.BaseEntity

class Notification extends BaseEntity {

    String subject

    String body

    NotificationStatus notificationStatus

    Customer customer

    Payment payment
}
