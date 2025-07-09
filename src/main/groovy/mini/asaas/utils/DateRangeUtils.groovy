package mini.asaas.utils

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class DateRangeUtils {

    public static Map<String, Date> getRangeUntilToday(int daysQuant) {
        if (daysQuant < 1) {
            throw new IllegalArgumentException("daysQuant must be >=1")
        }

        Calendar endCal = Calendar.getInstance()
        endCal.set(Calendar.HOUR_OF_DAY, 23)
        endCal.set(Calendar.MINUTE, 59)
        endCal.set(Calendar.SECOND, 59)
        endCal.set(Calendar.MILLISECOND, 999)
        Date end = endCal.getTime()

        Calendar startCal = Calendar.getInstance()
        startCal.add(Calendar.DATE, -daysQuant)
        startCal.set(Calendar.HOUR_OF_DAY, 0)
        startCal.set(Calendar.MINUTE, 0)
        startCal.set(Calendar.SECOND, 0)
        startCal.set(Calendar.MILLISECOND, 0)
        Date start = startCal.getTime()

        return [start: start, end: end]
    }
}
