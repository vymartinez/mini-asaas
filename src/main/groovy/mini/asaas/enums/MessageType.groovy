package mini.asaas.enums

enum MessageType {
    ERROR, INFO, SUCCESS, WARNING

    static MessageType convert(messageType) {
        try {
            if (messageType instanceof String) messageType = messageType.toUpperCase()
            return messageType as PersonType
        } catch(Exception e) {
            return null
        }
    }
}