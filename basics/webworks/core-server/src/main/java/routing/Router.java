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
            Class resourceClass = resourceConfig.getResources().stream()
                .filter(resource -> resource.isAnnotationPresent(Path.class))
                .filter(resource -> path.startsWith(((Path) resource.getAnnotation(Path.class)).value()))
                .findFirst().map(resource -> resource).get();
            String[] splitPath = path.split(((Path) resourceClass.getAnnotation(Path.class)).value());

            Method method = findMethod(resourceClass,
                    splitPath.length > 1 ? splitPath[1] : null,
                    methodString);
            Route route = new Route(path, new Endpoint(resourceClass, method));
            routes.put(path, route);
            return route;
        }
    }

    private static Method findMethod(Class resourceClass, String subpath, String method) {
        if (subpath == null) {
            return Arrays.stream(resourceClass.getMethods()).filter(m -> methodMatched(m, method)).findFirst().get();
        } else {
            return Arrays.stream(resourceClass.getMethods())
                    .filter(m -> m.isAnnotationPresent(Path.class))
                    .filter(m -> m.getAnnotation(Path.class).value().equals(subpath))
                    .findFirst().map(m -> m).get();
        }
    }

    private static boolean methodMatched(Method m, String method) {
        return Arrays.stream(m.getAnnotations())
                .filter(annotation -> annotation.toString().contains(method))
                .count() == 1;
    }
}
