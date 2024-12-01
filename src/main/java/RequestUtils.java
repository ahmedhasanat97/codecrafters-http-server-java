import java.util.Objects;

public class RequestUtils {

    public static Request toRequest(String requestLine) {
        String[] strings = requestLine.split(" ");
        String path = RequestUtils.extractRequestPath(strings);
        return new Request(path);
    }

    private static String extractRequestPath(String[] strings) {
        if (strings.length >= 2) {
            return strings[1];
        }
        throw new RuntimeException("Unable to extract the path from the request");
    }

}
