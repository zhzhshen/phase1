package resources;

import javax.inject.Inject;

public class TwoConstructorInjectShelf implements BookShelf {
    private Book book;

    @Inject
    public TwoConstructorInjectShelf(Book book) {
        this.book = book;
    }

    @Inject
    public TwoConstructorInjectShelf() {
    }

    public Book getBook() {
        return book;
    }
}
