package mini.asaas

import mini.asaas.enums.MessageType
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class BaseController {
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
}