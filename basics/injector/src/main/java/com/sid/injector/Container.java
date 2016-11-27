package com.sid.injector;

import com.sid.injector.exceptions.BindingException;
import com.sid.injector.exceptions.NoRegistrationException;
import com.sid.injector.injectPoint.ConstructorInjectPoint;
import com.sid.injector.injectPoint.FieldInjectPoint;
import com.sid.injector.injectPoint.InjectPoint;
import com.sid.injector.injectPoint.MethodInjectPoint;

import java.lang.annotation.Annotation;
import java.util.*;

public class Container {
    private Set<Class> registration = new HashSet<>();
    private Map<String, Object> bindings = new HashMap<>();
    private Map<Class, Object> qualifierBindings = new HashMap<>();

    public <T> T resolve(Class<T> klass) {
        if (registration.isEmpty() || !registration.contains(klass)) {
            throw new NoRegistrationException(klass);
        }

        InjectPoint injectPoint = new MethodInjectPoint(klass, new FieldInjectPoint(klass, new ConstructorInjectPoint(klass, "")));
        return injectPoint.resolve(this);
    }

    public Container register(Class klass) {
        registration.add(klass);
        return this;
    }

    public NamedBinding bind(Class klass) {
        return new NamedBinding(this, klass);
    }

    private <T> void bind(String name, T instance) {
        bindings.put(name, instance);
    }

    private <T> void bindQualifier(Class klass, T instance) {
        qualifierBindings.put(klass, instance);
    }

    public <T> T resolveBinding(String name, Annotation[] annotations) {
        Object object = null;
        if (name != null && !name.isEmpty()) {
            object = bindings.get(name);
        } else if (annotations != null && annotations.length != 0){
            object = Arrays.asList(annotations).stream().map(annotation -> qualifierBindings.get(annotation.annotationType())).filter(o -> o != null).findFirst().orElse(null);
        }
        return (T) object;
    }

    public QualifierBinding qualifier(Class klass) {
        return new QualifierBinding(this, klass);
    }

    public class NamedBinding {
        private final Container container;
        private final Class klass;
        private String name;

        private Object instance;

        NamedBinding(Container container, Class klass) {
            this.container = container;
            this.klass = klass;
        }

        public NamedBinding annotatedWith(String name) {
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

    public class QualifierBinding {
        private final Container container;

        private final Class klass;

        QualifierBinding(Container container, Class klass) {
            this.container = container;
            this.klass = klass;
        }

        public <T> Container toInstance(T instance) {
            bind(instance);
            return container;
        }
        private <T> void bind(T instance) {
            container.bindQualifier(klass, instance);
        }

    }
}
