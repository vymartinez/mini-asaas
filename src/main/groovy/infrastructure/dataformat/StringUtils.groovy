package infrastructure.dataformat

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class StringUtils {

    public static String camelCaseToSnakeCase(String text) {
        return text.replaceAll( /([A-Z])/, /_$1/ )
            .toLowerCase()
            .replaceAll( /^_/, '' )
    }
}