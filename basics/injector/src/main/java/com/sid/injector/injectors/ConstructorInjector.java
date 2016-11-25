package com.sid.injector.injectors;

import com.sid.injector.Container;
import com.sid.injector.exceptions.MultiConstructorInjectionException;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructorInjector implements Injector {
    private Class klass;
    private String namedValue;

    public ConstructorInjector(Class klass, String namedValue) {
        this.klass = klass;
        this.namedValue = namedValue;
    }

    @Override
    public <T> T resolve(Container container) {
        T object = container.resolveBinding(namedValue);
        if (object != null) {
            return object;
        }

        List<Constructor> injectConstructors = Arrays.asList(klass.getConstructors())
                .stream()
                .filter(constructor -> constructor.getAnnotation(Inject.class) != null)
                .collect(Collectors.toList());

        if (injectConstructors.size() > 1) {
            throw new MultiConstructorInjectionException(klass);
        } else if (injectConstructors.size() == 0) {
            return defaultConstructor();
        }

        Constructor constructor = injectConstructors.get(0);

        List parameters = Arrays.asList(constructor.getParameters())
                .stream()
                .map(parameter -> new ConstructorInjector(parameter.getType(), parameter.isAnnotationPresent(Named.class) ? parameter.getAnnotation(Named.class).value() : null).resolve(container))
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

}
