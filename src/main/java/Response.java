import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// please refer to https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_responses
public class Response<T> {

    private String protocol;
    private ResponseStatus status;
    private Map<String, String> headers;
    private T body;

    public Response() {
    }

    public Response(String protocol, ResponseStatus status, Map<String, String> headers, T body) {
        this.protocol = protocol;
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public void addHeader(String name, String value) {
        if (Objects.isNull(headers)) {
            headers = new HashMap<>();
        }
        headers.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(protocol).append(Constants.SPACE).append(status.getCode()).append(Constants.SPACE).append(status.getText())
                .append(Constants.CRLF);

        if (!Objects.isNull(headers)) {
            headers.forEach((k, v) -> {
                builder.append(k).append(Constants.COLON).append(Constants.SPACE).append(v).append(Constants.CRLF);
            });
        }

        builder.append(Constants.CRLF);

        builder.append(body);

        return builder.toString();
    }

}
