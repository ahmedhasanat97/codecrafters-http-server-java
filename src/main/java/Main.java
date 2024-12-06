import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String directoryPath = null;
        for (int i = 0; i < args.length - 1; i += 2) {
            if ("--directory".equals(args[i])) {
                directoryPath = args[i + 1];
            }
        }

        HttpServer httpServer = new HttpServer(4221, Runtime.getRuntime().availableProcessors(), directoryPath);
        httpServer.run();
    }

}
