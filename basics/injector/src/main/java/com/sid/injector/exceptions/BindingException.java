package com.sid.injector.exceptions;

public class BindingException extends RuntimeException {
    public <T> BindingException(Class klass, T instance) {
        super("object:" + instance + "is not an instance of class " + klass);
    }
}
