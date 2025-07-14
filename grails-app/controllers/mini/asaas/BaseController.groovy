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

    protected Integer getLimitPerPage() {
        Integer defaultLimitPerPage = (!params.itemsPerPage && !params.max) ? 10 : 50

        return getDefaultLimitPerPage(defaultLimitPerPage)
    }

    protected Integer getOffset() {
        if (params.containsKey("page")) {
            Integer currentPage = Integer.valueOf(params.page as Integer ?: 1)
            return (currentPage - 1) * getLimitPerPage()
        }

        if (params.offset == 'undefined') params.offset = null
        return Integer.valueOf(params.offset as Integer ?: 0)
    }

    protected void renderFile(def fileData, String fileName, Boolean download) {
        try {
            String contentType = "application/pdf"
            contentType = contentType ?: "application/octet-stream"

            if (download) {
                fileName = fileName.replaceAll("[^\\p{ASCII}]", "")
                fileName = fileName.replaceAll("\\s", "_")
                render(file: fileData, fileName: fileName, contentType: contentType)
            } else {
                render(file: fileData, contentType: contentType)
            }

        } catch (SocketException exception) {
            log.error("Erro ao gerar arquivo >> ${fileName}", exception)
        }
    }

    private Integer getDefaultLimitPerPage(Integer limitPerPage) {
        if (params.containsKey("itemsPerPage")) {
            String itemsPerPage = params.itemsPerPage?.toString()
            if (!itemsPerPage?.isNumber()) params.itemsPerPage = null

            params.itemsPerPage = params.itemsPerPage ? Integer.valueOf(params.itemsPerPage as String): limitPerPage

            return Math.min(params.itemsPerPage as Integer, limitPerPage)
        }

        String max = params.max?.toString()
        if (!max?.isNumber()) params.max = null
        params.max = params.max ? Integer.valueOf(params.max as String): limitPerPage

        return Math.min(params.max as Integer, limitPerPage)
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