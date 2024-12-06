import app.handlers.DummyHttpRequestHandler;
import http.server.HttpServer;
import http.server.common.HttpRequestHandler;

public class Main {

    public static void main(String[] args) {
        String directoryPath = null;
        for (int i = 0; i < args.length - 1; i += 2) {
            if ("--directory".equals(args[i])) {
                directoryPath = args[i + 1];
            }
        }

        HttpRequestHandler httpRequestHandler = new DummyHttpRequestHandler(directoryPath);
        HttpServer httpServer = new HttpServer(4221, Runtime.getRuntime().availableProcessors(), httpRequestHandler);
        httpServer.run();
    }

}
