import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HttpConnection implements Runnable {

    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final String directoryPath;

    public HttpConnection(Socket clientSocket, String directoryPath) throws IOException {
        System.out.println("new connection created");
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.directoryPath = directoryPath;
    }

    @Override
    public void run() {

        System.out.println("new connection running");

        try {
            String requestLine = null;
            List<String> headers = new ArrayList<>();

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Received: " + line);

                // Handle the request line
                if (Objects.isNull(requestLine)) {
                    requestLine = line;
                    continue;
                }

                if (line.isEmpty()) {
                    break;
                }

                headers.add(line);
            }

            Request request = RequestUtils.toRequest(requestLine, headers);

            handleResponse(out, request);

            in.close();
            out.close();
            clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void handleResponse(PrintWriter out, Request request) throws IOException {
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
        } else if (request.getPath().startsWith("/files/")) {
            response = new Response<>();
            response.setProtocol("HTTP/1.1");
            response.setStatus(ResponseStatus.OK);
            String fileName = request.getPath().substring(7);
            Path filePath = Path.of(directoryPath + fileName);
            if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
                response = new Response<>();
                response.setProtocol("HTTP/1.1");
                response.setStatus(ResponseStatus.NOT_FOUND);
            }

            List<String> lines = Files.readAllLines(filePath);
            StringBuilder fileContent = new StringBuilder();
            for (String line : lines) {
                fileContent.append(line);
            }

            response.setBody(fileContent.toString());
            response.addHeader(Headers.CONTENT_TYPE, "application/octet-stream");
            response.addHeader(Headers.CONTENT_LENGTH, String.valueOf(Optional.ofNullable(response.getBody()).map(v -> v.toString().length()).orElse(0)));
        } else {
            response = new Response<>();
            response.setProtocol("HTTP/1.1");
            response.setStatus(ResponseStatus.NOT_FOUND);
        }

        out.print(response);
        out.flush();
    }


}
