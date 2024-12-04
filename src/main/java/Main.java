import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4221);

            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            Socket clientSocket = serverSocket.accept();// Wait for connection from client.
            System.out.println("accepted new connection");

            // Create input and output streams
            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = clientSocket.getOutputStream();

            String requestLine = null;
            List<String> headers = new ArrayList<>();
            String requestBody = null;

            String line;
            while (!(line = reader.readLine()).trim().equals("")) {
                System.out.println("Received: " + line);

                if (Objects.isNull(requestLine)) {
                    requestLine = line;
                    continue;
                }

                if (!Objects.isNull(requestBody)) {
                    headers.add(requestBody);
                }

                requestBody = line;

            }

            Request request = RequestUtils.toRequest(requestLine, headers, requestBody);

            handleResponse(output, request);

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private static void handleResponse(OutputStream output, Request request) throws IOException {
        Response<Object> response;
        if (request.getPath().equals("/")) {
            response = new Response<>();
            response.setProtocol("HTTP/1.1");
            response.setStatus(ResponseStatus.OK);
        } else if (request.getPath().startsWith("/echo/")) {
            response = new Response<>();
            response.setProtocol("HTTP/1.1");
            response.setStatus(ResponseStatus.OK);
            response.setBody(request.getPath().substring(6));
            response.addHeader(Headers.CONTENT_TYPE, "text/plain");
            response.addHeader(Headers.CONTENT_LENGTH, String.valueOf(response.getBody().toString().length()));
        } else if (request.getPath().startsWith("/user-agent")) {
            response = new Response<>();
            response.setProtocol("HTTP/1.1");
            response.setStatus(ResponseStatus.OK);
            response.setBody(request.getHeader(Headers.USER_AGENT));
            response.addHeader(Headers.CONTENT_TYPE, "text/plain");
            response.addHeader(Headers.CONTENT_LENGTH, String.valueOf(Optional.ofNullable(response.getBody()).map(v -> v.toString().length()).orElse(0)));
        } else {
            response = new Response<>();
            response.setProtocol("HTTP/1.1");
            response.setStatus(ResponseStatus.NOT_FOUND);
        }

        output.write(response.toString().getBytes());
    }

}
