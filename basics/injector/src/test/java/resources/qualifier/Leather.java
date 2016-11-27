package resources.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@javax.inject.Qualifier
public @interface Leather {
    Color color() default Color.TAN;

    public enum Color {WHITE, TAN}
}
