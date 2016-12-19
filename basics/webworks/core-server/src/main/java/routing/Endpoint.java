package routing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.ServiceLocator;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Endpoint {
    private Method method;
    private Class resourceClass;

    public Endpoint(Class resourceClass, Method method) {
        this.resourceClass = resourceClass;
        this.method = method;
    }

    public String execute(ServiceLocator locator)
            throws IllegalAccessException, InvocationTargetException, JsonProcessingException {
        String[] typesString = method.isAnnotationPresent(Produces.class)
                ? method.getAnnotation(Produces.class).value()
                : null;
        MediaType type = MediaType.valueOf(typesString[0]);

        Object result = method.invoke(locator.getService(resourceClass));
        if (type.toString().equals(MediaType.APPLICATION_JSON)) {
            result = new ObjectMapper().writeValueAsString(result);
        }
        return (String) result;
    }
}
