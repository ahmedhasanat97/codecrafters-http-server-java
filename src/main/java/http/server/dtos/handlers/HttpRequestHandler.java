package http.server.dtos.handlers;

import http.server.dtos.request.Request;
import http.server.dtos.response.Response;

public interface HttpRequestHandler<T> {

    Response handle(Request request);

}
