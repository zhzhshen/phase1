package routing;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.glassfish.hk2.api.ServiceLocator;

import java.lang.reflect.InvocationTargetException;

public class Route {
    private final String path;
    private final Endpoint endpoint;

    public Route(String path, Endpoint endpoint) {
        this.path = path;
        this.endpoint = endpoint;
    }

    public String execute(ServiceLocator locator)
            throws IllegalAccessException, JsonProcessingException, InvocationTargetException {
        return endpoint.execute(locator);
    }
}
