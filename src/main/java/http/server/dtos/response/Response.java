package http.server.dtos.response;

import http.server.dtos.common.Headers;

import static http.server.common.Constants.CRLF;

public class Response {

    private ResponseStatusLine statusLine;
    private Headers headers;
    private ResponseBody body;

    public Response() {
    }

    public Response(ResponseStatusLine statusLine, Headers headers, ResponseBody body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public ResponseStatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(ResponseStatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public ResponseBody getBody() {
        return body;
    }

    public void setBody(ResponseBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return statusLine +
                CRLF +
                headers +
                CRLF +
                body;
    }

}
