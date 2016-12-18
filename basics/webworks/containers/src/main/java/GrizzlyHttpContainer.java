import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Method;
import java.util.Arrays;

public class GrizzlyHttpContainer extends HttpHandler {
    private ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);

    private ResourceConfig resourceConfig;

    public GrizzlyHttpContainer(ResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
        resourceConfig.register(locator);
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        Class resourceClass = resourceConfig.getResources()
                .stream()
                .filter(resource -> ((Path) resource.getAnnotation(Path.class)).value().equals(request.getHttpHandlerPath()))
                .findFirst().map(resource -> resource).get();

        Method method = findMethod(resourceClass, request.getMethod());

        String[] typesString = method.isAnnotationPresent(Produces.class)
                ? method.getAnnotation(Produces.class).value()
                : null;
        MediaType type = MediaType.valueOf(typesString[0]);

        Object result = method.invoke(locator.getService(resourceClass));
        if (type.toString().equals(MediaType.APPLICATION_JSON)) {
            result = new ObjectMapper().writeValueAsString(result);
        }

        response.getWriter().write((String) result);
    }

    private Method findMethod(Class resourceClass, org.glassfish.grizzly.http.Method method) {
        return Arrays.stream(resourceClass.getMethods()).filter(m -> methodMatched(m, method)).findFirst().get();
    }

    private boolean methodMatched(Method m, org.glassfish.grizzly.http.Method method) {
        return Arrays.stream(m.getAnnotations()).filter(annotation -> annotation.toString().contains(method.getMethodString())).count() == 1;
    }
}