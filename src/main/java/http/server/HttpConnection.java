package http.server;

import http.server.dtos.handlers.HttpRequestHandler;
import http.server.dtos.request.Request;
import http.server.dtos.response.Response;
import http.server.routes.HttpRoutes;
import http.server.utils.RequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class HttpConnection implements Runnable {

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final String directoryPath;

    public HttpConnection(Socket clientSocket, String directoryPath) throws IOException {
        System.out.println("new connection created");
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.directoryPath = directoryPath;
    }

    @Override
    public void run() {
        try {
            Request request = RequestUtils.readRequest(in);
            Response response = handleRequest(request);
            out.print(response);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeIn();
            closeOut();
            closeClientSocket();
        }
    }

    private void closeIn() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeOut() {
        out.close();
    }

    private void closeClientSocket() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request) {
        HttpRequestHandler<?> httpRequestHandler = HttpRoutes.getHttpRequestHandler(request);
        if (Objects.isNull(httpRequestHandler)) {
            throw new RuntimeException("unable to find the httpRequestHandler");
        }
        return httpRequestHandler.handle(request);
    }

//    private void handleResponse(PrintWriter out, Request request) throws IOException {
//        Response<Object> response;
//        if (request.getPath().equals("/")) {
//            response = new Response<>();
//            response.setProtocol("HTTP/1.1");
//            response.setStatus(ResponseStatus.OK);
//        } else if (request.getPath().startsWith("/echo/")) {
//            response = new Response<>();
//            response.setProtocol("HTTP/1.1");
//            response.setStatus(ResponseStatus.OK);
//            response.setBody(request.getPath().substring(6));
//            response.addHeader(HeadersConstants.CONTENT_TYPE, "text/plain");
//            response.addHeader(HeadersConstants.CONTENT_LENGTH, String.valueOf(response.getBody().toString().length()));
//        } else if (request.getPath().startsWith("/user-agent")) {
//            response = new Response<>();
//            response.setProtocol("HTTP/1.1");
//            response.setStatus(ResponseStatus.OK);
//            response.setBody(request.getHeader(HeadersConstants.USER_AGENT));
//            response.addHeader(HeadersConstants.CONTENT_TYPE, "text/plain");
//            response.addHeader(HeadersConstants.CONTENT_LENGTH, String.valueOf(Optional.ofNullable(response.getBody()).map(v -> v.toString().length()).orElse(0)));
//        } else if (request.getPath().startsWith("/files/")) {
//            response = new Response<>();
//            response.setProtocol("HTTP/1.1");
//            response.setStatus(ResponseStatus.OK);
//            String fileName = request.getPath().substring(7);
//            Path filePath = Path.of(directoryPath + fileName);
//            if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
//                response = new Response<>();
//                response.setProtocol("HTTP/1.1");
//                response.setStatus(ResponseStatus.NOT_FOUND);
//            } else {
//                List<String> lines = Files.readAllLines(filePath);
//                StringBuilder fileContent = new StringBuilder();
//                for (String line : lines) {
//                    fileContent.append(line);
//                }
//
//                response.setBody(fileContent.toString());
//                response.addHeader(HeadersConstants.CONTENT_TYPE, "application/octet-stream");
//                response.addHeader(HeadersConstants.CONTENT_LENGTH, String.valueOf(Optional.ofNullable(response.getBody()).map(v -> v.toString().length()).orElse(0)));
//            }
//        } else {
//            response = new Response<>();
//            response.setProtocol("HTTP/1.1");
//            response.setStatus(ResponseStatus.NOT_FOUND);
//        }
//
//        out.print(response);
//        out.flush();
//    }

}
