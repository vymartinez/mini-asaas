package mini.asaas.utils

class DomainUtils {

    public static Object addError(Object entity, String message) {
        entity.errors.reject("", null, message)

        return entity
    }
}
