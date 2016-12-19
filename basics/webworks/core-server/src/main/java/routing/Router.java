package routing;

import config.ResourceConfig;

import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private static Map<String, Route> routes = new HashMap<>();

    public static Route route(String path, String methodString, ResourceConfig resourceConfig) {
        if (routes.containsKey(path)) {
            return routes.get(path);
        } else {
            Class resourceClass = resourceConfig.getResources()
                .stream()
                .filter(resource -> ((Path) resource.getAnnotation(Path.class)).value().equals(path))
                .findFirst().map(resource -> resource).get();

            Method method = findMethod(resourceClass, methodString);
            Route route = new Route(path, new Endpoint(resourceClass, method));
            routes.put(path, route);
            return route;
        }
    }

    private static Method findMethod(Class resourceClass, String method) {
        return Arrays.stream(resourceClass.getMethods()).filter(m -> methodMatched(m, method)).findFirst().get();
    }

    private static boolean methodMatched(Method m, String method) {
        return Arrays.stream(m.getAnnotations()).filter(annotation -> annotation.toString().contains(method)).count() == 1;
    }
}
