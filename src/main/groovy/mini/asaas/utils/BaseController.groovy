package mini.asaas

import grails.compiler.GrailsCompileStatic
import mini.asaas.enums.MessageType

@GrailsCompileStatic
class BaseController {

    protected void buildFlashAlert(MessageType messageType, String message, Boolean success) {
        flash.type = messageType
        flash.message = message
        flash.success = success
    }
}