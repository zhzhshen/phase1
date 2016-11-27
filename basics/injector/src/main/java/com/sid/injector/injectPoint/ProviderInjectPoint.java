package com.sid.injector.injectPoint;


import com.sid.injector.Container;

import javax.inject.Provider;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ProviderInjectPoint implements InjectPoint {
    private Class klass;

    public ProviderInjectPoint(Type type) {
        this.klass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
    }

    @Override
    public <T> T resolve(Container container) {
        return (T) (Provider) () -> new ConstructorInjectPoint(klass, null).resolve(container);
    }
}
