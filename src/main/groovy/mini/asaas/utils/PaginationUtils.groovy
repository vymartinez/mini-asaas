package mini.asaas.utils

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
trait PaginationUtils {

    Map normalizePagination(Map params) {
        params.max = Math.min((params.max as Integer) ?: 10, 100)
        params.offset = (params.offset as Integer) ?: 0
        return params
    }
}
