import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final String SUCCESS_RESPONSE = "HTTP/1.1 200 OK\r\n\r\n";
    private static final String NOT_FOUND_RESPONSE = "HTTP/1.1 404 Not Found\r\n\r\n";

    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.out.println("Logs from your program will appear here!");

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

            // reading the request line
            String requestLine = reader.readLine();

            System.out.println("Received: " + requestLine);

            Request request = RequestUtils.toRequest(requestLine);

            handleResponse(output, request);

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private static void handleResponse(OutputStream output, Request request) throws IOException {
        Response<Object> response;
        if (request.getPath().equals("/")) {
            response = Response.builder()
                    .protocol("HTTP/1.1")
                    .status(ResponseStatus.OK)
                    .build();
        } else if (request.getPath().startsWith("/echo/")) {
            response = Response.builder()
                    .protocol("HTTP/1.1")
                    .status(ResponseStatus.OK)
                    .build();

            response.setBody(request.getPath().substring(6));

            response.addHeader(Headers.CONTENT_TYPE, "text/plain");
            response.addHeader(Headers.CONTENT_LENGTH, String.valueOf(response.getBody().toString().length()));
        } else {
            response = Response.builder()
                    .protocol("HTTP/1.1")
                    .status(ResponseStatus.NOT_FOUND)
                    .build();
        }

        output.write(response.toString().getBytes());
    }

}
