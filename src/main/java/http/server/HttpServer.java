package http.server;

import http.server.common.HttpRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer implements Runnable {

    private final int port;
    private final int numberOfThreads;
    private boolean acceptConnections = true;
    private final HttpRequestHandler httpRequestHandler;

    public HttpServer(int port, int numberOfThreads, HttpRequestHandler httpRequestHandler) {
        this.port = port;
        this.numberOfThreads = numberOfThreads;
        this.httpRequestHandler = httpRequestHandler;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port); ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads)) {
            System.out.println("the server running on port " + port + ", with " + numberOfThreads + " threads in the pool");
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            while (acceptConnections) {
                executorService.submit(new HttpConnection(serverSocket.accept(), httpRequestHandler));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
