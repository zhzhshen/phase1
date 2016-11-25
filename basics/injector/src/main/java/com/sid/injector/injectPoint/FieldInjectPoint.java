package com.sid.injector.injectPoint;

import com.sid.injector.Container;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldInjectPoint implements InjectPoint {
    private final Class klass;
    private final InjectPoint constructorInjectPoint;

    public <T> FieldInjectPoint(Class klass, InjectPoint constructorInjectPoint) {
        this.klass = klass;
        this.constructorInjectPoint = constructorInjectPoint;
    }

    @Override
    public <T> T resolve(Container container) {
        T object = constructorInjectPoint.resolve(container);
        Arrays.asList(klass.getFields()).stream().filter(field -> field.getAnnotation(Inject.class) != null).forEach(field -> resolveField(object, field, container));

        return object;
    }

    private <T> void resolveField(T object, Field field, Container container) {
        field.setAccessible(true);

        try {
            field.set(object, new ConstructorInjectPoint((Class) field.getType(), null).resolve(container));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(false);
    }
}
