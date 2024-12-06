package http.server;

import http.server.common.HttpRequestHandler;
import http.server.dtos.request.Request;
import http.server.dtos.response.Response;
import http.server.utils.RequestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpConnection implements Runnable {

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final HttpRequestHandler httpRequestHandler;

    public HttpConnection(Socket clientSocket, HttpRequestHandler httpRequestHandler) throws IOException {
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
        this.httpRequestHandler = httpRequestHandler;
    }

    @Override
    public void run() {
        try {
            Request request = RequestUtils.readRequest(in);
            Response response = httpRequestHandler.handle(request);
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

}
