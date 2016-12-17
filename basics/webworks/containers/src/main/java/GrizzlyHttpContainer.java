import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.BuilderHelper;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.Arrays;

public class GrizzlyHttpContainer extends HttpHandler {
    private ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);

    private ResourceConfig resourceConfig;

    public GrizzlyHttpContainer(ResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
        populate(locator, resourceConfig);
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        Class resourceClass = resourceConfig.getResources()
                .stream()
                .filter(resource -> ((Path) resource.getAnnotation(Path.class)).value().equals(request.getHttpHandlerPath()))
                .findFirst().map(resource -> resource).get();

        response.getWriter().write((String) findMethod(resourceClass, request.getMethod()).invoke(locator.getService(resourceClass)));
    }

    private Method findMethod(Class resourceClass, org.glassfish.grizzly.http.Method method) {
        return Arrays.stream(resourceClass.getMethods()).filter(m -> methodMatched(m, method)).findFirst().get();
    }

    private boolean methodMatched(Method m, org.glassfish.grizzly.http.Method method) {
        return Arrays.stream(m.getAnnotations()).filter(annotation -> annotation.toString().contains(method.getMethodString())).count() == 1;
    }

    private void populate(ServiceLocator locator, ResourceConfig resourceConfig) {
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();

        resourceConfig.getResources()
                .stream()
                .forEach(resourceClass -> config.bind(BuilderHelper.createDescriptorFromClass(resourceClass)));
        config.commit();
    }
}