package mini.asaas.utils

import grails.compiler.GrailsCompileStatic

import java.text.SimpleDateFormat

@GrailsCompileStatic
class DateUtils {
    public static final String ISO_DATE = 'yyyy-MM-dd'

    private static final ThreadLocal<SimpleDateFormat> ISO_FORMATTER = ThreadLocal.withInitial {
        new SimpleDateFormat(ISO_DATE)
    }

    public String formatDate(Date date) {
        date ? ISO_FORMATTER.get().format(date) : null
    }

    public static parseDate(String dateStr) {
        if (!dateStr) {
            return null
        }

        try {
            ISO_FORMATTER.get().parse(dateStr)
        } catch (Exception exception) {
            return null
        }
    }
}
