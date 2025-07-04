package mini.asaas

import mini.asaas.enums.NotificationType
import mini.asaas.utils.BaseEntity

class Notification extends BaseEntity {

    String subject

    String body

    NotificationType type

    Customer customer
}
