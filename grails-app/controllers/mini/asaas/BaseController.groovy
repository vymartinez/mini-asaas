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
        Integer defaultLimitPerPage = 50

        return getDefaultLimitPerPage(defaultLimitPerPage)
    }

    protected Integer getOffset() {
        int page = params.int('page') ?: 1

        int defaulOffset = (page - 1) * getLimitPerPage()

        Integer explicitOffset = params.int('offset')

        return explicitOffset != null ? explicitOffset : defaulOffset
    }

    private Integer getDefaultLimitPerPage(Integer limitPerPage) {
        Integer items = params.int('itemsPerPage')

        if (items != null) {
            return Math.min(items, limitPerPage)
        }

        Integer max = params.int('max')

        if (max != null) {
            return Math.min(max, limitPerPage)
        }

        return limitPerPage
    }
}