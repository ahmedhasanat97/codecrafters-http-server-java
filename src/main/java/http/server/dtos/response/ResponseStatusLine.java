package http.server.dtos.response;

import http.server.common.Constants;
import http.server.common.ResponseStatus;

public class ResponseStatusLine {

    private String protocol;
    private ResponseStatus status;

    public ResponseStatusLine() {
    }

    public ResponseStatusLine(String protocol, ResponseStatus status) {
        this.protocol = protocol;
        this.status = status;
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

    @Override
    public String toString() {
        return protocol +
                Constants.SPACE +
                status.getCode() +
                Constants.SPACE +
                status.getReasonPhrase();
    }

}
