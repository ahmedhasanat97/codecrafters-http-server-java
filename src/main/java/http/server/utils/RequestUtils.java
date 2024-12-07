package http.server.utils;

import http.server.common.HttpMethod;
import http.server.dtos.common.Headers;
import http.server.dtos.request.Request;
import http.server.dtos.request.RequestBody;
import http.server.dtos.request.RequestLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestUtils {

    public static Request readRequest(BufferedReader in) throws IOException {
        RequestLine requestLine = RequestUtils.readRequestLine(in);
        Headers headers = RequestUtils.readHeaders(in);
        RequestBody requestBody = RequestUtils.readRequestBody(in);

        return new Request(requestLine, headers, requestBody);
    }

    private static RequestLine readRequestLine(BufferedReader in) throws IOException {
        String requestLine = in.readLine();
        String[] strings = requestLine.split(" ");
        if (strings.length != 3) {
            throw new RuntimeException("Invalid request line format length");
        }
        return new RequestLine(HttpMethod.valueOf(strings[0]), strings[1], strings[2]);
    }

    private static Headers readHeaders(BufferedReader in) throws IOException {
        Headers headers = new Headers();

        String line;
        while ((line = in.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }

            Map.Entry<String, String> header = HeaderUtils.parseHeaderLine(line);
            headers.putHeader(header.getKey(), header.getValue());
        }

        return headers;
    }

    private static RequestBody readRequestBody(BufferedReader in) throws IOException {
        StringBuilder content = new StringBuilder();

        while (in.ready()) {
            char c = (char)in.read();
            content.append(c);
        }

        return new RequestBody(content.toString());
    }

}
