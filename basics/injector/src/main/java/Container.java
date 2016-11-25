import java.util.HashSet;
import java.util.Set;

public class Container {
    private Set<Class> registration = new HashSet<>();

    public <T> T resolve(Class<T> klass) {
        if (registration.isEmpty()) {
            throw new NoRegistrationException(klass);
        }
        return null;
    }

    public <T> Container register(Class<T> klass) {
        registration.add(klass);
        return this;
    }
}
