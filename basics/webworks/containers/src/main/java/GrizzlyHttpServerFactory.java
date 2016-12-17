import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;

import java.io.IOException;
import java.net.URI;

public class GrizzlyHttpServerFactory {

    public static HttpServer createHttpServer(final URI uri, final boolean start) {
        final String host = (uri == null || uri.getHost() == null) ? NetworkListener.DEFAULT_NETWORK_HOST : uri.getHost();
        final int port = (uri == null || uri.getPort() == -1)
                ? NetworkListener.DEFAULT_NETWORK_PORT
                : uri.getPort();

        final NetworkListener listener = new NetworkListener("grizzly", host, port);

        final HttpServer server = new HttpServer();
        server.addListener(listener);

        if (start) {
            try {
                server.start();
            } catch (IOException e) {
                server.shutdown();
                throw new RuntimeException("Failed to start a grizzly server");
            }
        }

        return server;
    }

    public static HttpServer createHttpServer(boolean start) {
        return createHttpServer(null, start);
    }
}
