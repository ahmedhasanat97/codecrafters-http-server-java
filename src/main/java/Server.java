import java.io.IOException;
import java.net.ServerSocket;

public class Server implements Runnable {

    private final ServerSocket serverSocket;
    private boolean acceptClients = true;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        // Since the tester restarts your program quite often, setting SO_REUSEADDR
        // ensures that we don't run into 'Address already in use' errors
        serverSocket.setReuseAddress(true);
    }

    @Override
    public void run() {
        while (acceptClients) {
            try {
                new Client(serverSocket.accept()).run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
