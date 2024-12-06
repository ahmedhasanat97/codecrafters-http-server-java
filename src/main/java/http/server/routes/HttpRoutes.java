package http.server.routes;

import http.server.common.HttpMethod;
import http.server.dtos.handlers.HttpRequestHandler;
import http.server.dtos.request.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRoutes {

     private static final Map<HttpMethod, List<HttpRoute>> httpMethodMapping = new HashMap<>();

     public void addRoute(HttpMethod httpMethod, String pattern, HttpRequestHandler<?> httpRequestHandler) {
         List<HttpRoute> httpRoutes = httpMethodMapping.getOrDefault(httpMethod, new ArrayList<>());
         httpRoutes.add(new HttpRoute(pattern, httpRequestHandler));
         httpMethodMapping.put(httpMethod, httpRoutes);
     }

     public static HttpRequestHandler<?> getHttpRequestHandler(Request request) {
         List<HttpRoute> httpRoutes = httpMethodMapping.get(request.getRequestLine().getHttpMethod());
         for (HttpRoute httpRoute: httpRoutes) {
             if (httpRoute.match(request.getRequestLine().getPath())) {
                 return httpRoute.getRequestHandler();
             }
         }
         return null;
     }

}
