package com.sid.injector;

import com.sid.injector.exceptions.BindingException;
import com.sid.injector.exceptions.NoRegistrationException;
import com.sid.injector.injectPoint.ConstructorInjectPoint;
import com.sid.injector.injectPoint.FieldInjectPoint;
import com.sid.injector.injectPoint.InjectPoint;
import com.sid.injector.injectPoint.MethodInjectPoint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Container {
    private Set<Class> registration = new HashSet<>();
    private Map<String, Object> bindings = new HashMap<>();

    public <T> T resolve(Class<T> klass) {
        if (registration.isEmpty() || !registration.contains(klass)) {
            throw new NoRegistrationException(klass);
        }

        InjectPoint injectPoint = new MethodInjectPoint(klass, new FieldInjectPoint(klass, new ConstructorInjectPoint(klass, null)));
        return injectPoint.resolve(this);
    }

    public Container register(Class klass) {
        registration.add(klass);
        return this;
    }

    public Binding bind(Class klass) {
        return new Binding(this, klass);
    }

    private <T> void bind(String name, T instance) {
        bindings.put(name, instance);
    }

    public <T> T resolveBinding(String name) {
        return (T) bindings.get(name);
    }

    public class Binding {
        private final Container container;
        private final Class klass;
        private String name;
        private Object instance;

        Binding(Container container, Class klass) {
            this.container = container;
            this.klass = klass;
        }

        public Binding annotatedWith(String name) {
            this.name = name;
            return this;
        }

        public <T> Container toInstance(T instance) {
            this.instance = instance;

            bind(instance);

            return container;
        }

        private <T> void bind(T instance) {
            if (instance.getClass().equals(klass)) {
                container.bind(name, this.instance);
            } else {
                throw new BindingException(klass, instance);
            }
        }
    }
}
