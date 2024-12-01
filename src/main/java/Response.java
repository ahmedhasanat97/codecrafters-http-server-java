import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// please refer to https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages#http_responses
public class Response<T> {

    private String protocol;
    private ResponseStatus status;
    private Map<String, String> headers;
    private T body;

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
