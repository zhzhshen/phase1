package exceptions;

public class NoRegistrationException extends RuntimeException {
    public NoRegistrationException(Class klass) {
        super("class " + klass + "is not registered");
    }
}
