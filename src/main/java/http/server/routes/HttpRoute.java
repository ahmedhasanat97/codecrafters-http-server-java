package http.server.routes;

import http.server.dtos.handlers.HttpRequestHandler;

public class HttpRoute {

    private final HttpRoutePattern pattern;
    private final HttpRequestHandler httpRequestHandler;

    public HttpRoute(String pattern, HttpRequestHandler httpRequestHandler) {
        this.pattern = new HttpRoutePattern(pattern);
        this.httpRequestHandler = httpRequestHandler;
    }

    public boolean match(String path) {
        return pattern.match(path);
    }

    public HttpRoutePattern getPattern() {
        return pattern;
    }

    public HttpRequestHandler getRequestHandler() {
        return httpRequestHandler;
    }

}
