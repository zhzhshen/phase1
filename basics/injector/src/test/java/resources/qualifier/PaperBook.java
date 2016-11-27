package resources.qualifier;

import resources.Book;

import javax.inject.Inject;

public class PaperBook extends Book{
    @Inject
    @Paper
    public Color color;

    public PaperBook() {
    }

    public Color getColor() {
        return color;
    }
}
