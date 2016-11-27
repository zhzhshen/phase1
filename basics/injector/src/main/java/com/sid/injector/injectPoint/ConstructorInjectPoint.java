package com.sid.injector.injectPoint;

import com.sid.injector.Container;
import com.sid.injector.exceptions.MultiConstructorInjectionException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorInjectPoint implements InjectPoint {
    private Class klass;
    private Annotation[] annotations;
    private String namedValue;

    public ConstructorInjectPoint(Class klass, String namedValue) {
        this.klass = klass;
        this.namedValue = namedValue;
    }

    public ConstructorInjectPoint(Class klass, Annotation[] annotations) {
        this.klass = klass;
        this.annotations = annotations;
    }

    @Override
    public <T> T resolve(Container container) {
        T object = null;
        if (klass.isAnnotationPresent(Singleton.class)) {
            object = container.resolveSingleton(klass);
            if (object != null) {
                return object;
            }
        } else {
            object = container.resolveBinding(namedValue, annotations);
            if (object != null) {
                object = new FieldInjectPoint(object).resolve(container);
                container.bindSingleton(klass, object);
                return object;
            }
        }

        List<Constructor> injectConstructors = Arrays.asList(klass.getConstructors())
                .stream()
                .filter(constructor -> constructor.getAnnotation(Inject.class) != null)
                .collect(Collectors.toList());

        if (injectConstructors.size() > 1) {
            throw new MultiConstructorInjectionException(klass);
        } else if (injectConstructors.size() == 0) {
            object = defaultConstructor();
        } else {
            object = parameterConstructor(container, injectConstructors.get(0));
        }
        container.bindSingleton(klass, object);
        return object;
    }

    private <T> T parameterConstructor(Container container, Constructor injectConstructors) {
        Constructor constructor = injectConstructors;

        List parameters = Arrays.asList(constructor.getParameters())
                .stream()
                .map(parameter -> new ConstructorInjectPoint(parameter.getType(), parameter.isAnnotationPresent(Named.class) ? parameter.getAnnotation(Named.class).value() : null).resolve(container))
                .collect(Collectors.toList());
        try {
            return (T) constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T defaultConstructor() {
        try {
            return (T) klass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
