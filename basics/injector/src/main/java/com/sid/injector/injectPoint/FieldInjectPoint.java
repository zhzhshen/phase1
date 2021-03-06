package com.sid.injector.injectPoint;

import com.sid.injector.Container;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Field;
import java.util.Arrays;

public class FieldInjectPoint implements InjectPoint {
    private final Class klass;
    private Object object;
    private final InjectPoint constructorInjectPoint;

    public FieldInjectPoint(Class klass, InjectPoint constructorInjectPoint) {
        this.klass = klass;
        this.constructorInjectPoint = constructorInjectPoint;
    }

    public <T> FieldInjectPoint(T object) {
        klass = object.getClass();
        constructorInjectPoint = new ConstructorInjectPoint(object.getClass(), "");
        this.object = object;
    }

    @Override
    public <T> T resolve(Container container) {
        if (object == null) {
            object = constructorInjectPoint.resolve(container);
        }
        Arrays.asList(klass.getFields()).stream().filter(field -> field.getAnnotation(Inject.class) != null).forEach(field -> resolveField(object, field, container));

        return (T) object;
    }

    private <T> void resolveField(T object, Field field, Container container) {
        field.setAccessible(true);

        try {
            ConstructorInjectPoint constructorInjectPoint;
            if (field.isAnnotationPresent(Named.class)) {
                constructorInjectPoint = new ConstructorInjectPoint(field.getType(), field.getAnnotation(Named.class).value());
            } else {
                constructorInjectPoint = new ConstructorInjectPoint(field.getType(), field.getAnnotations());
            }
            field.set(object, new FieldInjectPoint(field.getType(), constructorInjectPoint).resolve(container));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        field.setAccessible(false);
    }
}
