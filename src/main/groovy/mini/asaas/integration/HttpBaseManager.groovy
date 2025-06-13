package mini.asaas.integration

import groovy.transform.CompileStatic
import org.apache.commons.lang.NotImplementedException

@CompileStatic
trait HttpBaseManager {

    Integer timeout

    Map responseBody

    Integer statusCode

    public void get(String path, Map queryParams) {
        throw new NotImplementedException()
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout
    }

    public Integer getStatus() {
        return this.statusCode
    }

    public Map getResponseBody() {
        return this.responseBody
    }

    public abstract Boolean isSuccessful()

    private abstract String buildUrl(String path)

    private abstract Map buildHeader()
}