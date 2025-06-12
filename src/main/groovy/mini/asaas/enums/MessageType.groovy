package mini.asaas.enums

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
enum MessageType {
    ERROR,
    INFO,
    SUCCESS,
    WARNING

    public static MessageType convert(messageType) {
        try {
            if (messageType instanceof String) messageType = messageType.toUpperCase()
            return messageType as MessageType
        } catch(Exception e) {
            return null
        }
    }
}