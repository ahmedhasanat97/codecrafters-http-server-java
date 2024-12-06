package http.server;

import http.server.routes.HttpRoutes;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer implements Runnable {

    private final int port;
    private final int numberOfThreads;
    private boolean acceptConnections = true;
    private final String directoryPath;
    private final HttpRoutes httpRoutes;

    public HttpServer(int port, int numberOfThreads, String directoryPath, HttpRoutes httpRoutes) {
        this.port = port;
        this.numberOfThreads = numberOfThreads;
        this.directoryPath = directoryPath;
        this.httpRoutes = httpRoutes;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port); ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads)) {
            System.out.println("the server running on port " + port + ", with " + numberOfThreads + " threads in the pool");
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            while (acceptConnections) {
                System.out.println("waiting for a new connection");
                executorService.submit(new HttpConnection(serverSocket.accept(), directoryPath, httpRoutes));
                System.out.println("new connection received");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
