package http.server.dtos.request;

import http.server.common.HttpMethod;

public class RequestLine {

    private HttpMethod httpMethod;
    private String path;
    private String protocol;

    public RequestLine() {
    }

    public RequestLine(HttpMethod httpMethod, String path, String protocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.protocol = protocol;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
