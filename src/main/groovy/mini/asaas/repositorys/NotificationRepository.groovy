package mini.asaas.repositorys

import mini.asaas.Notification

import grails.compiler.GrailsCompileStatic
import infrastructure.repository.Repository
import org.grails.datastore.mapping.query.api.BuildableCriteria

@GrailsCompileStatic
class NotificationRepository implements Repository<Notification, NotificationRepository> {

    @Override
    public void buildCriteria() {
        addCriteria {
            if (search.containsKey("status")) {
                eq("notificationStatus", search.status.toString())
            }

            if (search.containsKey("customerId")) {
                eq("customer.id", search.customerId)
            }

            if (search.containsKey("paymentId")) {
                eq("payment.id", search.paymentId)
            }
        }
    }

    public BuildableCriteria getBuildableCriteria() {
        return Notification.createCriteria()
    }

    @Override
    public List<String> listAllowedFilters() {
        return [
            "status",
            "customerId",
            "paymentId",
        ]
    }
}
