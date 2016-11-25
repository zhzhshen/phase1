package resources;

import javax.inject.Inject;
import javax.inject.Named;

public class Book {
    @Inject
    @Named("name")
    public String name;

    public String getName() {
        return name;
    }
}
