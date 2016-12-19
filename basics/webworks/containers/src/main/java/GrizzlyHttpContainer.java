import config.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import routing.Route;
import routing.Router;

public class GrizzlyHttpContainer extends HttpHandler {
    private ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);

    private ResourceConfig resourceConfig;

    public GrizzlyHttpContainer(ResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
        resourceConfig.registerResources(locator);
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        Route route = Router.route(request.getHttpHandlerPath(),
                request.getMethod().getMethodString(),
                resourceConfig);

        response.getWriter().write(route.execute(locator));
    }

}