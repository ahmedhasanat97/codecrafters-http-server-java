package app.handlers;

import http.server.common.ResponseStatus;
import http.server.dtos.request.Request;
import http.server.dtos.response.Response;
import http.server.dtos.response.ResponseStatusLine;

public class Handlers {

    public Response sayNothing(Request request) {
        Response response = new Response();
        ResponseStatusLine responseStatusLine = new ResponseStatusLine(request.getRequestLine().getProtocol(), ResponseStatus.OK);
        response.setStatusLine(responseStatusLine);
        return response;
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
