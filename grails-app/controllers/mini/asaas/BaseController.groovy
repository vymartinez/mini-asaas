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
}