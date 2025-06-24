package mini.asaas

import mini.asaas.enums.MessageType
import mini.asaas.user.User
import mini.asaas.userdetails.CustomUserDetails
import grails.plugin.springsecurity.SpringSecurityService

class BaseController {

    SpringSecurityService springSecurityService

    protected void buildFlashAlert(String message, MessageType messageType, Boolean success) {
        flash.type = messageType
        flash.message = message
        flash.success = success
    }

    protected Integer getLimitPerPage() {
        Integer defaultLimitPerPage = (!params.itemsPerPage && !params.max) ? 10 : 50

        return getDefaultLimitPerPage(defaultLimitPerPage)
    }

    protected Integer getOffset() {
        if (params.containsKey("page")) {
            Integer currentPage = Integer.valueOf(params.page ?: 1)
            return (currentPage - 1) * getLimitPerPage()
        }

        if (params.offset == 'undefined') params.offset = null
        return Integer.valueOf(params.offset ?: 0)
    }

    private Integer getDefaultLimitPerPage(Integer limitPerPage) {
        if (params.containsKey("itemsPerPage")) {
            String itemsPerPage = params.itemsPerPage?.toString()
            if (!itemsPerPage?.isNumber()) params.itemsPerPage = null

            params.itemsPerPage = params.itemsPerPage ? Integer.valueOf(params.itemsPerPage): limitPerPage

            return Math.min(params.itemsPerPage, limitPerPage)
        }

        String max = params.max?.toString()
        if (!max?.isNumber()) params.max = null
        params.max = params.max ? Integer.valueOf(params.max): limitPerPage

        return Math.min(params.max, limitPerPage)
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