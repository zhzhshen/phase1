package resources.constructor;

import resources.Book;
import resources.BookShelf;

import javax.inject.Inject;
import javax.inject.Named;

public class NamedParameterShelf implements BookShelf {
    private Book book;

    @Inject
    public NamedParameterShelf(@Named("theBook") Book book) {
        this.book = book;
    }

    @Override
    public Book getBook() {
        return book;
    }
}
