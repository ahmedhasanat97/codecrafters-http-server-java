package http.server.dtos.common;

import http.server.common.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static http.server.common.Constants.CRLF;

public class Headers {

    private final Map<String, String> headers;

    public Headers() {
        headers = new HashMap<>();
    }

    public void putHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getHeader(String name) {
        return Optional.ofNullable(name).map(String::toLowerCase).map(headers::get).orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        headers.forEach((k, v) -> builder.append(k).append(Constants.COLON).append(Constants.SPACE).append(v).append(CRLF));
        return builder.toString();
    }

}
