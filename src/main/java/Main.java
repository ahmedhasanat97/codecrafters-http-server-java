import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = new HttpServer(4221, Runtime.getRuntime().availableProcessors());
        httpServer.run();
    }

}
