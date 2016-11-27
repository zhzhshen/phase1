package resources.qualifier;

import javax.inject.Inject;

import static resources.qualifier.Leather.*;

public class Book {
    @Inject
    @Leather
    public Color color;

    public Book() {
    }

    public Color getColor() {
        return color;
    }
}
