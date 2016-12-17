import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.Arrays;

public class GrizzlyHttpContainer extends HttpHandler {
    private ResourceConfig resourceConfig;

    public GrizzlyHttpContainer(ResourceConfig resourceConfig) {
        this.resourceConfig = resourceConfig;
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        Class resourceClass = resourceConfig.getResources()
                .stream()
                .filter(resource -> ((Path) resource.getAnnotation(Path.class)).value().equals(request.getHttpHandlerPath()))
                .findFirst().map(resource -> resource).get();

        response.getWriter().write((String) findMethod(resourceClass, request.getMethod()).invoke(resourceClass.newInstance()));
    }

    private Method findMethod(Class resourceClass, org.glassfish.grizzly.http.Method method) {
        return Arrays.stream(resourceClass.getMethods()).filter(m -> methodMatched(m, method)).findFirst().get();
    }

    private boolean methodMatched(Method m, org.glassfish.grizzly.http.Method method) {
        return Arrays.stream(m.getAnnotations()).filter(annotation -> annotation.toString().contains(method.getMethodString())).count() == 1;
    }
}