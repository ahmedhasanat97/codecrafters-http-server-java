package app.handlers;

import http.server.common.HeadersConstants;
import http.server.common.HttpMethod;
import http.server.common.HttpRequestHandler;
import http.server.common.ResponseStatus;
import http.server.dtos.common.Headers;
import http.server.dtos.request.Request;
import http.server.dtos.response.Response;
import http.server.dtos.response.ResponseBody;
import http.server.dtos.response.ResponseStatusLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class DummyHttpRequestHandler implements HttpRequestHandler {

    private String directoryPath;

    public DummyHttpRequestHandler() {
    }

    public DummyHttpRequestHandler(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public Response handle(Request request) {
        try {
            String path = request.getRequestLine().getPath();
            HttpMethod httpMethod = request.getRequestLine().getHttpMethod();

            ResponseStatusLine responseStatusLine = new ResponseStatusLine(request.getRequestLine().getProtocol(), ResponseStatus.OK);
            Headers headers = new Headers();
            ResponseBody body = new ResponseBody();

            Response response = new Response(responseStatusLine, headers, body);

            if (path.equals("/")) {
                // nothings
            } else if (path.startsWith("/echo/")) {
                body.setContent(path.substring(6));
                headers.putHeader(HeadersConstants.CONTENT_TYPE, "text/plain");
                headers.putHeader(HeadersConstants.CONTENT_LENGTH, String.valueOf(response.getBody().toString().length()));
            } else if (path.startsWith("/user-agent")) {
                body.setContent(request.getHeaders().getHeader(HeadersConstants.USER_AGENT));
                headers.putHeader(HeadersConstants.CONTENT_TYPE, "text/plain");
                headers.putHeader(HeadersConstants.CONTENT_LENGTH, String.valueOf(Optional.ofNullable(response.getBody()).map(v -> v.toString().length()).orElse(0)));
            } else if (HttpMethod.GET.equals(httpMethod) && path.startsWith("/files/")) {
                String fileName = path.substring(7);
                Path filePath = Path.of(directoryPath + fileName);
                if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
                    responseStatusLine.setStatus(ResponseStatus.NOT_FOUND);
                } else {
                    List<String> lines = Files.readAllLines(filePath);
                    StringBuilder fileContent = new StringBuilder();
                    for (String line : lines) {
                        fileContent.append(line);
                    }

                    body.setContent(fileContent.toString());
                    headers.putHeader(HeadersConstants.CONTENT_TYPE, "application/octet-stream");
                    headers.putHeader(HeadersConstants.CONTENT_LENGTH, String.valueOf(Optional.ofNullable(response.getBody().getContent()).map(String::length).orElse(0)));
                }
            } else if (HttpMethod.POST.equals(httpMethod) && path.startsWith("/files/")) {
                String fileName = path.substring(7);
                File file = new File(directoryPath + "/" + fileName);

                if (file.createNewFile()) {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(request.getRequestBody().getContent());
                    fileWriter.flush();
                    fileWriter.close();
                }

                body.setContent(request.getRequestBody().getContent());
                headers.putHeader(HeadersConstants.CONTENT_TYPE, "application/octet-stream");
                headers.putHeader(HeadersConstants.CONTENT_LENGTH, String.valueOf(Optional.ofNullable(response.getBody().getContent()).map(String::length).orElse(0)));
            } else {
                responseStatusLine.setStatus(ResponseStatus.NOT_FOUND);
            }

            return response;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
