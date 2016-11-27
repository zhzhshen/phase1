package resources.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static resources.qualifier.Leather.*;

@Documented
@Retention(RUNTIME)
@javax.inject.Qualifier
public @interface Paper {
    Color color() default Color.WHITE;
}
