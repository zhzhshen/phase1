public class ConstructorInjector implements Injector {
    private Class klass;

    public ConstructorInjector(Class klass) {
        this.klass = klass;
    }

    @Override
    public <T> T inject() {
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
