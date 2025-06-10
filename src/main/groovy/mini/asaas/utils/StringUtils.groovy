package mini.asaas.utils

class StringUtils {

    public static String removeNonNumeric(String text) {
        if (text == null) return null

        return text?.replaceAll("\\D+","")
    }
}
