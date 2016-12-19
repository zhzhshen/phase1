import config.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpHandlerRegistration;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;

import java.io.IOException;
import java.net.URI;

public class GrizzlyHttpServerFactory {

    public static HttpServer createHttpServer(boolean start) {
        return createHttpServer(null, start);
    }

    public static HttpServer createHttpServer(final URI uri, final boolean start) {
        return createHttpServer(uri, null, start);
    }

    public static HttpServer createHttpServer(URI uri, ResourceConfig resourceConfig, boolean start) {
        final String host = (uri == null || uri.getHost() == null) ? NetworkListener.DEFAULT_NETWORK_HOST : uri.getHost();
        final int port = (uri == null || uri.getPort() == -1)
                ? NetworkListener.DEFAULT_NETWORK_PORT
                : uri.getPort();

        final NetworkListener listener = new NetworkListener("grizzly", host, port);

        final HttpServer server = new HttpServer();
        server.addListener(listener);

        final ServerConfiguration config = server.getServerConfiguration();
        if (resourceConfig != null) {
            final String path = uri.getPath().replaceAll("/{2,}", "/");

            final String contextPath = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
            config.addHttpHandler(new GrizzlyHttpContainer(resourceConfig), HttpHandlerRegistration.bulder().contextPath(contextPath).build());
        }

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
}
