import java.util.Map;
import java.util.Objects;

public class Request {

    private String path;
    private Map<String, String> headers;

    public Request() {
    }

    public Request(String path, Map<String, String> headers) {
        this.path = path;
        this.headers = headers;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getHeader(String headerName) {
        if (Objects.isNull(headers) || Objects.isNull(headerName)) {
            return null;
        }
        return headers.get(headerName.toLowerCase());
    }

}
