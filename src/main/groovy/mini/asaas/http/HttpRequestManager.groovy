package mini.asaas.http

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient

import org.apache.http.Header
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.params.BasicHttpParams
import org.apache.http.params.HttpConnectionParams
import org.apache.http.params.HttpParams

class HttpRequestManager {

    static final Integer defaultTimeout = 30000

    Integer timeout

    String url

    String path

    Map requestHeaders = [:]

    def requestBody = [:]

    Boolean success = false

    Integer responseHttpStatus

    String responseBody

    Map responseHeaderMap = [:]

    Map responseBodyMap

    RESTClient restClient

    String errorMessage

    public HttpRequestManager(String url, Map requestHeaders, requestBody, Integer timeout = null) {
        this.url = url
        this.requestHeaders = requestHeaders
        this.requestBody = requestBody
        this.timeout = timeout ?: HttpRequestManager.defaultTimeout
        this.restClient = new RESTClient(this.url)

        restClient.client = buildHttpClient()
    }

    public void get() {
        try {
            HttpResponseDecorator apiResponse = restClient.get( headers: ["Accept": "application/json"] + requestHeaders, requestContentType: ContentType.JSON, path: path, query: requestBody )
            processResponse(apiResponse)
        } catch (Exception e) {
            handleRequestException(e)
        } finally {
            restClient.shutdown()
        }
    }

    private void setResponseBodyObject(HttpResponseDecorator apiResponse) {
        try {
            this.responseBodyMap = apiResponse.getData()
        } catch (Exception e) {
            this.responseBodyMap = null
        }
    }

    private DefaultHttpClient buildHttpClient() {
        final HttpParams httpParams = new BasicHttpParams()
        HttpConnectionParams.setConnectionTimeout(httpParams, this.timeout)
        HttpConnectionParams.setSoTimeout(httpParams, this.timeout)
        return new DefaultHttpClient(httpParams)
    }

    private void processResponse(HttpResponseDecorator apiResponse) {
        this.responseHttpStatus = apiResponse.getStatus()
        this.responseBody = apiResponse.getData()

        setResponseHeader(apiResponse)
        setResponseBodyObject(apiResponse)

        this.success = true
    }

    private void setResponseHeader(HttpResponseDecorator apiResponse) {
        if (!apiResponse?.getAllHeaders()?.asBoolean()) return

        for (Header header : apiResponse.getAllHeaders()) {
            this.responseHeaderMap.put(header.getName(), header.getValue())
        }
    }

    private void handleRequestException(Exception exception) {
        def detailedException = exception.getCause() ?: exception
        this.errorMessage = exception.getMessage()

        if (detailedException instanceof HttpResponseException) {
            this.responseHttpStatus = exception.getResponse().getStatus()
            this.responseBody = exception.getResponse().getData()
        }
    }
}