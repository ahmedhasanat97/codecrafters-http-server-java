package app.routes;

import app.handlers.Handlers;
import http.server.common.HttpMethod;
import http.server.routes.HttpRoutes;

public class Routes {

    private final static Handlers handlers = new Handlers();

    public static HttpRoutes httpRoutes() {
        HttpRoutes httpRoutes = new HttpRoutes();
        httpRoutes.addRoute(HttpMethod.GET, "/", handlers::sayNothing);
        return httpRoutes;
    }

}
