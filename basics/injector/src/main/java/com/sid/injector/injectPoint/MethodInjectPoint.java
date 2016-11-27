package com.sid.injector.injectPoint;

import com.sid.injector.Container;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodInjectPoint implements InjectPoint {
    private final Class klass;
    private final InjectPoint fieldInjectPoint;

    public MethodInjectPoint(Class klass, InjectPoint fieldInjectPoint) {
        this.klass = klass;
        this.fieldInjectPoint = fieldInjectPoint;
    }

    @Override
    public <T> T resolve(Container container) {
        Object object = fieldInjectPoint.resolve(container);

        Arrays.asList(klass.getMethods()).stream().filter(method -> method.getAnnotation(Inject.class) != null).forEach(method -> resolveMethod(object, method, container));

        return (T) object;
    }

    private <T> void resolveMethod(T object, Method method, Container container) {
        List<Object> parameters = Arrays.asList(method.getParameters()).stream().map(parameter -> new FieldInjectPoint(parameter.getType(), new ConstructorInjectPoint(parameter.getType(), null)).resolve(container)).collect(Collectors.toList());

        try {
            method.invoke(object, parameters.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
