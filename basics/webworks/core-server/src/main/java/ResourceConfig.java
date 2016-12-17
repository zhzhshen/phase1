import java.util.ArrayList;
import java.util.List;

public class ResourceConfig {
    private List<Class> resources = new ArrayList();

    public ResourceConfig(Class klass) {
        resources.add(klass);
    }

    public List<Class> getResources() {
        return resources;
    }
}
