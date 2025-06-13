package mini.asaas.utils

import com.google.gson.GsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class GsonBuilderUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    public static <T> T buildClassFromJsonWithType(String json, Class<T> clazz) {
        String responseAsJson = JsonOutput.toJson(new JsonSlurper().parseText(json))
        return (T) createGsonBuilder().fromJson(responseAsJson, clazz)
    }

    private static createGsonBuilder() {
        return new GsonBuilder().setDateFormat(GsonBuilderUtils.DATE_FORMAT).create()
    }
}
