package resources;

import javax.inject.Inject;

public class Shelf implements BookShelf {

    private Book book;

    @Inject
    public Shelf(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
