package mini.asaas.utils

import grails.compiler.GrailsCompileStatic
import org.grails.datastore.gorm.GormValidateable

@GrailsCompileStatic
class DomainUtils {

    public static GormValidateable addError(GormValidateable entity, String message) {
        entity.errors.reject("", null, message)

        return entity
    }
}
