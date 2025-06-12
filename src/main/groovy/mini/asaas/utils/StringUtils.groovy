package mini.asaas.utils

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class StringUtils {

    public static String removeNonNumeric(String text) {
        if (text == null) return null

        return text?.replaceAll("\\D+","")
    }
}
