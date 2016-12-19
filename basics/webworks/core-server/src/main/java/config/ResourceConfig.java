package config;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;

import java.util.ArrayList;
import java.util.List;

public class ResourceConfig {
    private List<Class> resources = new ArrayList<>();

    public ResourceConfig(Class klass) {
        resources.add(klass);
    }

    public List<Class> getResources() {
        return resources;
    }

    public void registerResources(ServiceLocator locator) {
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();

        resources.forEach(resourceClass -> config.bind(BuilderHelper.createDescriptorFromClass(resourceClass)));
        config.commit();
    }
}
