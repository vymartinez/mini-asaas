package mini.asaas.integration.viacep

import groovy.transform.CompileStatic
import mini.asaas.http.HttpRequestManager
import mini.asaas.integration.HttpBaseManager
import org.springframework.http.HttpStatus

@CompileStatic
class ViaCepManager implements HttpBaseManager {

    HttpRequestManager httpRequestManager

    public void get(String path) {
        this.httpRequestManager = new HttpRequestManager(buildUrl(path), [:], null, timeout)
        this.httpRequestManager.get()
        this.processResponse()
    }

    public Boolean isSuccessful() {
        return this.httpRequestManager.responseHttpStatus == HttpStatus.OK.value()
    }

    private processResponse() {
        this.statusCode = this.httpRequestManager.responseHttpStatus
        this.responseBody = this.httpRequestManager.responseBodyMap
    }

    private String buildUrl(String path) {
        return "https://viacep.com.br/ws${path}"
    }
}
