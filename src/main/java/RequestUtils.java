import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtils {

    public static Request toRequest(String requestLine, List<String> headers, String requestBody) {
        String path = RequestUtils.extractRequestPath(requestLine);
        Map<String, String> parsedHeaders = RequestUtils.extractRequestHeaders(headers);
        return new Request(path, parsedHeaders);
    }

    private static String extractRequestPath(String requestLine) {
        String[] strings = requestLine.split(" ");
        if (strings.length >= 2) {
            return strings[1];
        }
        throw new RuntimeException("Unable to extract the path from the request");
    }

    private static Map<String, String> extractRequestHeaders(List<String> headers) {
        Map<String, String> parsedHeaders = new HashMap<>();

        headers.forEach(header -> {
            int indexOfFirstColon = header.indexOf(':');
            String key = header.substring(0, indexOfFirstColon).trim().toLowerCase();
            String value = header.substring(indexOfFirstColon).trim();
            parsedHeaders.put(key, value);
        });

        return parsedHeaders;
    }

}
