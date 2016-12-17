import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ResourceTest {
    @Test
    public void should_inject_resource () {
        ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);

        HelloWorldResource service = locator.create(HelloWorldResource.class);

        assertThat(service, notNullValue());
        assertThat(service.get(), is("Hello World!"));
    }

    @Test
    public void should_inject_resource_with_descriptor () {
        ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();

        config.bind(BuilderHelper.createDescriptorFromClass(HelloWorldResource.class));
        config.commit();

        HelloWorldResource service = locator.getService(HelloWorldResource.class);

        assertThat(service, notNullValue());
        assertThat(service.get(), is("Hello World!"));
    }
}
