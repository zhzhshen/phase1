package resources.qualifier;

import resources.Book;

import javax.inject.Inject;

public class LeatherBook extends Book {
    @Inject
    @Leather
    public Color color;

    public LeatherBook() {
    }

    public Color getColor() {
        return color;
    }
}
