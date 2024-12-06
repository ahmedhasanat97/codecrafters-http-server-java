package http.server.common;

import http.server.dtos.request.Request;
import http.server.dtos.response.Response;

public interface HttpRequestHandler {

    Response handle(Request request);

}
