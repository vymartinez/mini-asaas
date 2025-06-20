package mini.asaas

import mini.asaas.enums.MessageType
import mini.asaas.user.User
import mini.asaas.userdetails.CustomUserDetails

import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.SpringSecurityService

@GrailsCompileStatic
class BaseController {

    SpringSecurityService springSecurityService

    protected void buildFlashAlert(String message, MessageType messageType, Boolean success) {
        flash.type = messageType
        flash.message = message
        flash.success = success
    }

    protected User getCurrentUser() {
        return springSecurityService.currentUser as User
    }

    protected Long getCurrentUserId() {
        return springSecurityService.currentUserId as Long
    }

    protected Customer getCurrentCustomer() {
        Customer customer = Customer.get(getUserDetails()?.customerId)
        if (customer) return customer

        return getCurrentUser()?.customer
    }

    protected Long getCurrentCustomerId() {
        Long customerId = getUserDetails()?.customerId
        if (customerId) return customerId

        return getCurrentCustomer()?.id
    }

    private CustomUserDetails getUserDetails() {
        Object userDetails = springSecurityService.getPrincipal()
        if (userDetails && userDetails instanceof CustomUserDetails) return userDetails

        return null
    }
}