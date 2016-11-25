package injectors;

public interface Injector {
    <T> T inject();
}
